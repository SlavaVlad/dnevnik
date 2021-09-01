package well.keepitsimple.dnevnik

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import well.keepitsimple.dnevnik.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import android.widget.ArrayAdapter
import com.google.firebase.firestore.DocumentSnapshot


class MainActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient
    val F: String = "Firebase"
    private val RC_SIGN_IN: Int = 123

    var uid: String? = null
    val user_doc: DocumentSnapshot? = null

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configure Google Sign In
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener {
            val intent = Intent(this, AddHomework::class.java)
            startActivity(intent)
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    // START LOGIN
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if ( currentUser == null) {
            signIn()
        } else {
            checkUserInDatabase(currentUser.uid)
            uid = currentUser.uid
            db.collection("users").document(uid.toString()).get().addOnSuccessListener {

            }
        }
    }
    private fun startFioDialog(uid: String) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("В базе вас нет :(")
        val dialogLayout = inflater.inflate(R.layout.alert_dialog_fio, null)
        val editText1 = dialogLayout.findViewById<EditText>(R.id.d_et_last_name)
        val editText2 = dialogLayout.findViewById<EditText>(R.id.d_et_name)
        val editText3 = dialogLayout.findViewById<EditText>(R.id.d_et_patronymic)
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { dialogInterface, i ->
            if (editText1.text.toString() != "" && editText2.text.toString() != "" && editText3.text.toString() != "") {

                val data = hashMapOf(
                    "last_name" to editText1.text.toString(),
                    "name" to editText2.text.toString(),
                    "patronymic" to editText3.text.toString()
                )

                db.collection("users").document(uid).set(data)

                this.uid = uid

            } else {
                startFioDialog(uid)
            }
        }
        builder.show()
    }
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                Log.d(F, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(F, "Google sign in failed", e)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(F, "signInWithCredential:success")
                    val user = auth.currentUser

                        checkUserInDatabase(user!!.uid)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(F, "signInWithCredential:failure", task.exception)
                }
            }
    }
    private fun checkUserInDatabase(uid: String) {

        val docRef = db.collection("users").document(uid)
        docRef.get()
                .addOnSuccessListener { document ->
                    if (document.exists()){
                        Log.w(F, "Ты есть в базе")
                    } else {
                        startFioDialog(uid)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(F, "Error getting documents: ", exception)
                }


    }
    // END LOGIN

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}