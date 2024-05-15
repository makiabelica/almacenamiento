package edu.sv.ufg.fis.amb.almacenamientointernoexterno

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import android.Manifest
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 101
    lateinit var boton: Button
    lateinit var texto: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        escrituraArchivosAlmacenamientoInterno(this, "archivo_almacenamiento_interno.txt", "ESTE ES UN CONTENIDO DE ALMACENAMIENTO INTERNO")

        escrituraArchivosAlmacenamientoExterno(this, "archivo_almacenamiento_externo.txt", "ESTE ES UN CONTENIDO DE ALMACENAMIENTO externo")

        boton = findViewById<Button>(R.id.btn_guardar)
        texto = findViewById<EditText>(R.id.txt_data)

        boton.setOnClickListener {
            escrituraArchivosAlmacenamientoInterno(this,"archivo_con_valor_campo_texto.txt", texto.text.toString())
        }
    }


    fun escrituraArchivosAlmacenamientoInterno(context: Context, fileName: String, content: String){
        val filepath = context.filesDir.absolutePath+"/$fileName"
        val file = File(filepath)
        try {
            file.writeText(content)
            Log.v("Escritura en almacenamiento local", "RUTA 🪑: '${filepath}'");
        }catch (e: Exception){
            e.printStackTrace();
        }
    }


    fun escrituraArchivosAlmacenamientoExterno(context: Context, fileName: String, content: String){
        if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.Q){
            val filePath = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!.absolutePath+"/$fileName"
            val file = File(filePath)

            try {
                file.writeText(content)
                Log.v("Escritura en almacenamiento externo", "RUTA : '${filePath}'");
            }catch (e: Exception){
                e.printStackTrace();
            }
        }else{
            val filepath = context.getExternalFilesDir(null)!!.absolutePath+"/$fileName"
            val file = File(filepath)
            if (ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                try {
                    file.writeText(content)
                    Log.v("Escritura en almacenamiento", "RUTA 🪑: '${filepath}'");
                }catch (e: Exception){
                    e.printStackTrace();
                }
            }else{
                ActivityCompat.requestPermissions((context as Activity), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
            }
        }
    }
}