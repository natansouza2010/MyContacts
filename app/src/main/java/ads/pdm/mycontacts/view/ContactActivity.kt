package ads.pdm.mycontacts.view

import ads.pdm.mycontacts.databinding.ActivityContactBinding
import ads.pdm.mycontacts.model.Constant.EXTRA_CONTACT
import ads.pdm.mycontacts.model.Constant.VIEW_CONTACT
import ads.pdm.mycontacts.model.Contact
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.random.Random


class ContactActivity : AppCompatActivity() {
    private val acb: ActivityContactBinding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(acb.root)

        val receivedContact = intent.getParcelableExtra<Contact>(EXTRA_CONTACT);

        receivedContact?.let{ _receivedContact ->
            acb.nameEt.setText(_receivedContact.name)
            acb.addressEt.setText(_receivedContact.adress)
            acb.phoneEt.setText(_receivedContact.phone)
            acb.emailEt.setText(_receivedContact.email)

        }

        val viewContact = intent.getBooleanExtra(VIEW_CONTACT,false)
        if(viewContact){
            acb.nameEt.isEnabled = false
            acb.addressEt.isEnabled = false
            acb.phoneEt.isEnabled = false
            acb.emailEt.isEnabled = false
            acb.saveBt.isEnabled = false
        }



        acb.saveBt.setOnClickListener {
            val contact = Contact(
                id = receivedContact?.id?: Random(System.currentTimeMillis()).nextInt(),
                name = acb.nameEt.text.toString(),
                adress = acb.addressEt.text.toString(),
                phone = acb.phoneEt.text.toString(),
                email = acb.emailEt.text.toString(),
            )

            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_CONTACT, contact)
            setResult(RESULT_OK, resultIntent)
            finish()

        }

    }
}