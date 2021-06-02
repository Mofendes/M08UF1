package cat.copernic.finals.carlos.demoretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import cat.copernic.finals.carlos.demoretrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.Math.abs

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener  {

    // http://www.xtec.cat/~jmendez1/forteco/courses.json

    // Gesture Detector
    private lateinit var gestureDetector: GestureDetector
    private var x2:Float = 0.0f
    private var x1:Float = 0.0f
    private var y2:Float = 0.0f
    private var y1:Float = 0.0f

    companion object{
            const val MIN_DISTANCE = 150
    }


    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter: CursoAdapter
    private val itemsFromPep = mutableListOf<Curso>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gestureDetector = GestureDetector(this,this)

        // View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        // View Binding
        //setContentView(R.layout.activity_main)
        setContentView(binding.root)

        // Ejemplo Databinding
        binding.ivRotativa.setOnClickListener {
            animation()
        }

        // Descarga de datos
        searchByName("")
        // Iniciamos el recycler
        initRecyclerView()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        gestureDetector.onTouchEvent(event)

        when(event?.action){
            // Swipe start
            0->{
                x1 = event.x
                y1 = event.y
            }
            // Release
            1->{
                x2 = event.x
                y2 = event.y

                val valueX:Float = x2-x1
                val valueY:Float = y2-y1

                /*
                if(abs(valueX) > MIN_DISTANCE){
                    // Swipes horizontales
                    if (x2 > x1){
                        //Swipe right
                        //Toast.makeText(this, "Swipe right!", Toast.LENGTH_SHORT)
                    }else{
                        //Swipe left
                        //Toast.makeText(this, "Swipe left!", Toast.LENGTH_SHORT)
                    }
                }else */
                if(abs(valueY) > MIN_DISTANCE){
                // Swipes verticales
                    if (y2 > y1){
                        //Swipe down
                        searchByName("")
                    }
                    /*else{
                        //Swipe up
                        //Toast.makeText(this, "Swipe up!", Toast.LENGTH_SHORT)
                    }
                    */
                }

            }
        }
        return super.onTouchEvent(event)
    }


    // Método para hacer la animación
    private fun animation() {
        binding.ivRotativa.animate().apply {
            duration = 500
            rotationYBy(360f) // La dif, entre rorationXBy y rotationX es que el By continua desde tu punto actual de animación
            //}.start()
        }.withEndAction {
            binding.ivRotativa.animate().apply {
                duration = 1000
                rotationYBy(3600f)
            }.start()
        }

    }

    // Construye el recyclerView
    private fun initRecyclerView() {
        // primero el adapter
        adapter = CursoAdapter(itemsFromPep)
        // luego el layout
        binding.rvCurso.layoutManager = LinearLayoutManager(this)

        // el adapter antes creado, al adapter del recycler view
        binding.rvCurso.adapter = adapter
    }

    // Creación del retrofit
    private fun getRetrofit(): Retrofit{

        return Retrofit.Builder()
            .baseUrl("http://www.xtec.cat/~jmendez1/forteco/") // courses.json
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    // Funcion con corrutinas para cargar los datos en el retrofit consumiendo la API
    private fun searchByName(query:String){

        // De forma asíncrona a inflar la vista lanzamos una corrutina para hacer la consulta
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getCursos("courses.json") // ("$query") // query = courses.json
            val lista = call.body()

            // para acoplarlo al MainThread
            runOnUiThread {
                if (call.isSuccessful){
                    // Show recyclerView
                    //Log.i("CLG", "Mapea el recycler?") //
                    // Si la lista del API está vacía, hacemos un emptyList() para que no sea null
                    val itemsPep = lista ?: emptyList()

                    itemsFromPep.clear()
                    itemsFromPep.addAll(itemsPep)
                    adapter.notifyDataSetChanged()

                    animation()

                }else{
                    //Log.i("CLG", "Error") //
                    showError()
                }
            }
        }

    }

    // En caso de error de consumo de API
    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error al recibir los datos", Toast.LENGTH_SHORT)
    }

    // Control de gestos
    override fun onDown(e: MotionEvent?): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onShowPress(e: MotionEvent?) {
        //TODO("Not yet implemented")
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
        //TODO("Not yet implemented")
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        //TODO("Not yet implemented")
        return false
    }


}