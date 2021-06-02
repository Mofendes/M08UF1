package cat.copernic.finals.carlos.demoretrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.finals.carlos.demoretrofit.databinding.ItemListBinding
import com.squareup.picasso.Picasso


class CursoAdapter(private val listDatos: List<Curso>):
        RecyclerView.Adapter<CursoAdapter.ViewHolderCursos>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCursos {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ViewHolderCursos(layoutInflater.inflate(R.layout.item_list, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolderCursos, position: Int) {
            val item = listDatos[position]
            holder.render(item)
        }

        override fun getItemCount(): Int = listDatos.size


    // ViewHolder Class
    class ViewHolderCursos(view:View):RecyclerView.ViewHolder(view) {

        // View Binding es el del return (ViewHolderCursos(layoutInflater.inflate(R.layout.item_list, parent, false)))
        private val binding = ItemListBinding.bind(view)

        fun render(curso: Curso){

            // Numero de seminario
            binding.tvNumeroSeminari.text = curso.numSeminari
            // titulo
            binding.tvTitol.text = curso.titol
            // Empresa organizadora
            binding.tvEmpresaOrganitzadora.text = curso.empresa
            // Cargar la imagen
            Picasso.get().load(curso.imagen).into(binding.ivLogo)

        }
    }
}