Kotlonpackage hu.bme.aut.publictransport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import hu.bme.aut.publictransport.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
	private lateinit var binding: ActivityLoginBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		Thread.sleep(1000)
		setTheme(R.style.Theme_PublicTransport)
		binding = ActivityLoginBinding.inflate(layoutInflater)
		binding.btnLogin.setOnClickListener {
			val email = binding.etEmailAddress.text.toString()
			val emailRegex = Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")

			when {
				email.isEmpty() -> {
					binding.etEmailAddress.requestFocus()
					binding.etEmailAddress.error = "Please enter your email address"
				}
				!email.matches(emailRegex) -> {
					binding.etEmailAddress.requestFocus()
					binding.etEmailAddress.error = "Please enter a valid email address"
				}
				binding.etPassword.text.toString().isEmpty() -> {
					binding.etPassword.requestFocus()
					binding.etPassword.error = "Please enter your password"
				}
				else -> {
					startActivity(Intent(this, ListActivity::class.java))
				}
			}
		}
		setContentView(binding.root)
	}
}