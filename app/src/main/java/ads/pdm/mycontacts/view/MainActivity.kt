package ads.pdm.mycontacts.view

import ads.pdm.mycontacts.R
import ads.pdm.mycontacts.adapter.ContactAdapter
import ads.pdm.mycontacts.databinding.ActivityMainBinding
import ads.pdm.mycontacts.model.Constant
import ads.pdm.mycontacts.model.Constant.EXTRA_CONTACT
import ads.pdm.mycontacts.model.Contact
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //data source
    private val contactList: MutableList<Contact> = mutableListOf()

    //Adapter
//    private val contactAdapter: ArrayAdapter<String> by lazy {
//        ArrayAdapter(
//            this,
//            android.R.layout.simple_list_item_1,
//            contactList.map{it.toString()}
//        )
//    }
     private lateinit var contactAdapter: ContactAdapter
//    private val contactAdapter =  ContactAdapter(this, contactList)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        fillContactList()
        contactAdapter = ContactAdapter(this, contactList)
        amb.contactListView.adapter = contactAdapter

        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),

        ){
            result ->
                if(result.resultCode == RESULT_OK){
                    val contact = result.data?.getParcelableExtra<Contact>(EXTRA_CONTACT)
                    contact?.let{
                        _contact ->
                        val position = contactList.indexOfFirst { it.id ==_contact.id  }
                        if(position != -1){
                            //alterar a posicao

                            contactList[position] = _contact
                        }else {
                            contactList.add(_contact)
                        }

//                        contactAdapter.add(_contact.toString())
                        contactAdapter.notifyDataSetChanged()
                    }
                }
        }

        registerForContextMenu(amb.contactListView)
        amb.contactListView.onItemClickListener = object: AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val contact = contactList[position]
                val contactIntent = Intent(this@MainActivity, ContactActivity::class.java)
                contactIntent.putExtra(EXTRA_CONTACT, contact)
                contactIntent.putExtra(EXTRA_CONTACT, false)
                startActivity(contactIntent)
            }


        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private lateinit var carl: ActivityResultLauncher<Intent>

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {
            R.id.addContactMenuItem -> {
                carl.launch(Intent(this, ContactActivity::class.java))

                true
            }
            else -> {false}
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main,menu )
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        return when(item.itemId) {
            R.id.removeContactM1 -> {
                //Remove o contato
                contactList.removeAt(position)
                Log.d("AOBA", contactList.toString());
                contactAdapter.notifyDataSetChanged()
                true

            }
            R.id.editContactM1 -> {
                val contact = contactList[position]
                val contactIntent = Intent(this, ContactActivity::class.java)
                contactIntent.putExtra(EXTRA_CONTACT, contact)
                carl.launch( contactIntent)
                //Chama a tela para editar contato
                true
            }
            else -> { false }

        }
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