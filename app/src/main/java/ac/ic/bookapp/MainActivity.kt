package ac.ic.bookapp

import ac.ic.bookapp.data.LoanDatasource
import ac.ic.bookapp.data.LoginRepository
import ac.ic.bookapp.databinding.ActivityMainBinding
import ac.ic.bookapp.messaging.MessageService
import ac.ic.bookapp.model.LoanRequest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sendbird.android.SendBird
import com.sendbird.android.SendBirdException
import com.sendbird.android.handlers.InitResultHandler
import java.util.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "Creating Main Activity")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigationView
        val navHost =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        val navController = navHost.navController
        bottomNavigationView.setupWithNavController(navController)
        setupNotificationCount(bottomNavigationView)

        val handler: InitResultHandler = object: InitResultHandler {
            override fun onInitFailed(e: SendBirdException) {
                Log.d(TAG, "SendBird Init failed")
            }

            override fun onInitSucceed() {
                Log.d(TAG, "SendBird Init succeeded")
                MessageService.connectToSendBird(LoginRepository.getUserId().toString(),
                    LoginRepository.getUsername(), this@MainActivity) {
                    MessageService.createChannelHandler(setupMessageNotification(bottomNavigationView))
                }
            }

            override fun onMigrationStarted() {
                Log.d(TAG, "SendBird updated")
            }
        }
        SendBird.init("07375028-AE3C-4FC9-9D5D-428AE1B180B6", this, true, handler)

        Log.d(TAG, "Main Activity created")
    }

    private fun setupNotificationCount(bottomNavigationView: BottomNavigationView) {
        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                val notifCount = getLoanRequests().size
                if (notifCount > 0)
                    bottomNavigationView.getOrCreateBadge(R.id.notifsFragment).number = notifCount
                mainHandler.postDelayed(this, 1000)
            }
        })

    }

    private fun getLoanRequests(): List<LoanRequest> =
        LoanDatasource.getUserIncomingLoanRequests(
            LoginRepository.getUserId()
        )

    private fun setupMessageNotification(bottomNavigationView: BottomNavigationView): () -> Unit {
        return {
            bottomNavigationView.getOrCreateBadge(R.id.messagingFragment)
        }
    }

}

