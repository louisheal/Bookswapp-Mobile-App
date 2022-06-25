package ac.ic.bookapp

import ac.ic.bookapp.data.LoanDatasource
import ac.ic.bookapp.databinding.ActivityMainBinding
import ac.ic.bookapp.filesys.LoginPreferences
import ac.ic.bookapp.model.LoanRequest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sendbird.android.SendBird

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "Creating Main Activity")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SendBird.init("07375028-AE3C-4FC9-9D5D-428AE1B180B6", this)

        val bottomNavigationView = binding.bottomNavigationView
        val navHost =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        val navController = navHost.navController
        bottomNavigationView.setupWithNavController(navController)
        setupNotificationCount(bottomNavigationView)

        Log.d(TAG, "Main Activity created")
    }

    private fun setupNotificationCount(bottomNavigationView: BottomNavigationView) {
        val notifCount = getLoanRequests().size
        if (notifCount > 0)
            bottomNavigationView.getOrCreateBadge(R.id.notifsFragment).number = notifCount
    }

    private fun getLoanRequests(): List<LoanRequest> =
        LoanDatasource.getUserIncomingLoanRequests(
            LoginPreferences.getUserLoginId(this)
        )


}

