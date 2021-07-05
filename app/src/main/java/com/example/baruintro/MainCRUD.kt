package com.example.baruintro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baruintro.adapter.SQLiteHelper
import com.example.baruintro.adapter.StudentAdapter
import com.example.baruintro.model.StudentModel

class MainCRUD : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edEmail: EditText
    private lateinit var edDate: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: StudentAdapter? = null
    private var std:StudentModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_crud)
        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener{ addStudent() }
        btnView.setOnClickListener{ getStudent() }
        btnUpdate.setOnClickListener{ updateStudent()}

        adapter?.setOnClickItem {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
            edName.setText(it.name)
            edEmail.setText(it.email)
            edDate.setText(it.date)
            std = it
        }

        adapter?.setOnClickDeleteItem {
            deleteStudent(it.id)
        }
    }

    private fun getStudent(){
        val stdList = sqLiteHelper.getAllStudent()
        Log.e("pppp", "${stdList.size}")

        adapter?.addItems(stdList)
    }
    private fun addStudent() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()
        val date = edDate.text.toString()

        if(name.isEmpty() || email.isEmpty() ){
            Toast.makeText(this, "Please enter requried field", Toast.LENGTH_SHORT).show()
        } else {
            val std = StudentModel(name =name, email = email)
            val status = sqLiteHelper.insertStudent(std)
            //check insert success or not success
            if(status > -1){
                Toast.makeText(this, "Student Added....", Toast.LENGTH_SHORT).show()
                clearEditText()
                getStudent()
            } else {
                Toast.makeText(this, "Record not saved....", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun updateStudent(){
        val name = edName.text.toString()
        val email = edEmail.text.toString()
        val date = edDate.text.toString()

        if(name ==std?.name && email == std?.email){
            Toast.makeText(this, "Record not Changed...", Toast.LENGTH_SHORT).show()
            return
        }
        if (std == null) return

        val std = StudentModel(id = std!!.id, name = name, email = email, date = date)
        val status = sqLiteHelper.updateStudent(std)
        if(status > -1){
            clearEditText()
            getStudent()
        }else {
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
        }

    }

    private fun deleteStudent(id: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete item?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){dialog, _ ->
            sqLiteHelper.deleteStudentById(id)
            getStudent()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){dialog, _ ->

            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun clearEditText(){
        edName.setText("")
        edEmail.setText("")
        edDate.setText("")
        edName.requestFocus()
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter
    }


    private fun initView(){
        edName = findViewById(R.id.edName)
        edEmail = findViewById(R.id.edEmail)
        edDate = findViewById(R.id.edDate)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)
    }
}