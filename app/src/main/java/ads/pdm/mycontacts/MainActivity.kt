package ads.pdm.mycontacts

import ads.pdm.mycontacts.databinding.ActivityMainBinding
import ads.pdm.mycontacts.model.Contact
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //data source
    private val contactList: MutableList<Contact> = mutableListOf()

    //Adapter
    private val contactAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            contactList.map{it.toString()}
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        fillContactList()
        amb.contactListView.adapter = contactAdapter

    }

    private fun fillContactList(){
        for(i in 1 .. 50){
            contactList.add(
                Contact(
                    id = i,
                    name = "Nome $i",
                    adress = "Endereço $i",
                    phone = "Telefone  $i",
                    email = "Email  $i",
                )
            )

        }
    }
}