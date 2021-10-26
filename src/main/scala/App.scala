import java.sql.DriverManager
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import scala.io.StdIn

object App {

  def main(args: Array[String]) {

    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/projectdb"
    val username = "lhnguyen38"
    val password = "p@ssword123"
    
    var connection:Connection = DriverManager.getConnection(url, username, password)
    val statement = connection.createStatement()
    
    val step1 = statement.executeQuery("SELECT * FROM laptop_models;")
    //println(step1)
    while ( step1.next() ) {
      println("Supported laptop models:")
      println(step1.getString(1)+". " +step1.getString(2))
    }

    print("Select your laptop model ID: ")
    var input = StdIn.readLine()

    println("Available parts for this model:")
    val step2 = statement.executeQuery("SELECT part_id,part_desc from models_have_parts " +
      "WHERE model_id="+input+";")
    while ( step2.next() ) {
      println(step2.getString(1)+". " +step2.getString(2))
    }
    print("Select category ID: ")
    input = StdIn.readLine()

    val step3 = statement.executeQuery("SELECT part_id,partname,partnumber,quantity,price " +
      "FROM parts WHERE part_id="+input+";")
    while ( step3.next() ) {
      // lists all description for the selected item
      print("Item name: ")
      println(step3.getString(2))
      print("MPN: ")
      println(step3.getString(3))
      print("Quantity in Stock: ")
      println(step3.getString(4))
      print("Price: $")
      println(step3.getString(5))
        if (step3.getInt(4)==0) {
          println("We are out of stock for this item")
        }
    }


    connection.close()
  }

}