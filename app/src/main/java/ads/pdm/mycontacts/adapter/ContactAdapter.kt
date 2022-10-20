package ads.pdm.mycontacts.adapter

import ads.pdm.mycontacts.R
import ads.pdm.mycontacts.databinding.TileContactBinding
import ads.pdm.mycontacts.model.Contact
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ContactAdapter(context: Context,
                     private val contactList: MutableList<Contact>): ArrayAdapter<Contact>(context, R.layout.tile_contact, contactList) {

    private data class TitleContactHolder(val nameTv: TextView, val emailTv: TextView)
    private lateinit var tcb: TileContactBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contact = contactList.get(position)
        var contactTitleView = convertView
        if(contactTitleView == null ){
            //inflar uma nova célula
            tcb = TileContactBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent, false
            )
//           val view = (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
//                R.layout.tile_contact, parent, false
//            )
            contactTitleView = tcb.root
            val titleContactHolder = TitleContactHolder(tcb.nameTv, tcb.emailTv)
            contactTitleView.tag = titleContactHolder
        }

        tcb.nameTv.text = contact.name
        tcb.emailTv.text = contact.email
        return contactTitleView
    }

}