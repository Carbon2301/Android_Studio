package com.example.studentmanagement

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagement.controller.AddStudentFragment
import com.example.studentmanagement.controller.FragmentSingleton
import com.example.studentmanagement.controller.StudentAdapter
import com.example.studentmanagement.database.StudentData
import com.example.studentmanagement.model.Student
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), OnStudentFragment {

    private lateinit var studentAdapter: StudentAdapter
    private lateinit var students: MutableList<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Setup DrawerLayout and NavigationView
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navController: NavController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setupWithNavController(navController)

        students = StudentData.students
        studentAdapter = StudentAdapter(students, this)

        findViewById<RecyclerView>(R.id.recycler_view_students).run {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        setupNav()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_student -> {
                showAddStudentFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        return NavigationUI.navigateUp(navController, drawerLayout) || super.onSupportNavigateUp()
    }

    private fun setupNav(){
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.list_student_nav -> {
                    // Navigate to the List Student Fragment
                    findNavController(R.id.nav_host_fragment).navigate(R.id.listStudentFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.add_student_nav -> {
                    // Navigate to the Add Student Fragment
                    showAddStudentFragment()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.edit_student_nav -> {
                    // Navigate to the Edit Student Fragment
                    FragmentSingleton.getInstance().student?.let {
                        showEditStudentFragment(FragmentSingleton.getInstance().position,
                            it
                        )
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun showAddStudentFragment() {
        FragmentSingleton.getInstance().onStudentAddedListener = object : AddStudentFragment.OnStudentAddedListener {

            override fun onStudentAdded(name: String, id: String) {
                Log.i("add", "add")
                students.add(Student(name, id))
                studentAdapter.notifyItemInserted(students.size - 1)
            }
        }
        findNavController(R.id.nav_host_fragment).navigate(R.id.addStudentFragment)
    }

    override fun showRemoveStudentFragment(position: Int, student: Student) {
        //findNavController(R.id.nav_host_fragment).navigate(R.id.removeStudentFragment)
    }

    override fun showEditStudentFragment(position: Int, student: Student) {
        FragmentSingleton.getInstance().studentActionListener = studentAdapter
        FragmentSingleton.getInstance().student = student
        FragmentSingleton.getInstance().position = position
        findNavController(R.id.nav_host_fragment).navigate(R.id.editStudentFragment)
    }
}



public interface OnStudentFragment{
    fun showAddStudentFragment()
    fun showRemoveStudentFragment(position: Int, student: Student)
    fun showEditStudentFragment(position: Int, student: Student)
}
