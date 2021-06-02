package cat.copernic.finals.carlos.demoretrofit

import com.google.gson.annotations.SerializedName

data class Curso (@SerializedName("numero_seminari") var numSeminari:String ,
                  @SerializedName("titol")  var titol:String,
                  @SerializedName("descripcio") var desc:String,
                  @SerializedName("empresa_organitzadora") var empresa:String,
                  @SerializedName("places_reservades") var plazas:String,
                  @SerializedName("logo") var imagen:String
)