import java.time.LocalDate

open class Book(title:String,author:String){
    val title =title.lowercase()
    val author=author.replaceFirstChar { it.uppercase() }

}
class BookItem(val BID:Int,val bookType:BookType, var status: BookStatus=BookStatus.Available, title: String, author: String): Book(title, author
) {
        var issueDate: LocalDate? = null
}

enum class BookStatus{
    Available,Issued,Lost,ReadOnly,Reserved
}
enum class BookType{
    Drama,Fiction,Horror,Romance,Biography,Historic,Magazine,Journal
}

fun main() {
    val l1=Library("Paramount Library","221B Baker Street")
    val admin=Admin()
    // adding students to the Library
    val s1=Student(101,"apurv", "roy")
    val s2=Student(102, "abhishek",  "raj")
    val s3=Student(103, "Kevin", "ray")
    val s4=Student(104,"Riya","Singh")
    val s5=Student(105,"anushka","sharma")
    val f1=Faculty(1001,"Ashish","Khanna")
    val f2=Faculty(1002,"Namita","Gupta")

    l1.addStudentToLibrary(s1)
    l1.addStudentToLibrary(s2)
    l1.addStudentToLibrary(s3)
    l1.addStudentToLibrary(s4)
    l1.addStudentToLibrary(s5)
    l1.addFacultyToLibrary(f1)
    l1.addFacultyToLibrary(f2)

    //adding books to the Library
    val b1=BookItem(901,BookType.Drama, BookStatus.Available, "The White Tiger",
        "Aravind Adiga")
    val b2=BookItem(902,BookType.Historic,BookStatus.Available,"The Great Indian Novel",
        "Shashi Tharoor")
    val b3=BookItem(905,BookType.Drama,BookStatus.Available,"Train to Pakistan",
        "Khushwant Singh")
    val b4=BookItem(716,BookType.Romance,BookStatus.Available,"A Suitable Boy",
        "Vikram Seth")
    val b5=BookItem(882,BookType.Biography,BookStatus.Available,"Playing It My Way",
    "Sachin Tendulkar")
    val b6=BookItem(501,BookType.Magazine,BookStatus.ReadOnly,"Digit",
        "Nine Dot Nine Interactive Pvt Ltd")
    val b7=BookItem(502,BookType.Magazine,BookStatus.ReadOnly,"Outlook",
        "Outlook Publishing India Pvt Ltd")
    val b8=BookItem(512,BookType.Historic,BookStatus.Reserved,"India After Gandhi","Ramachandra Guha")
    val b9=BookItem(813,BookType.Fiction,BookStatus.Reserved,"The Godfather","Mario Puzo")


    l1.addBooksToLibrary(b1)
    l1.addBooksToLibrary(b2)
    l1.addBooksToLibrary(b3)
    l1.addBooksToLibrary(b4)
    l1.addBooksToLibrary(b5)
    l1.addBooksToLibrary(b6)
    l1.addBooksToLibrary(b7)
    l1.addBooksToLibrary(b8)
    l1.addBooksToLibrary(b9)
//    print(l1)
    //TODO
    //menu for library
    println("Welcome to the ${l1.name}")
    println("Enter the User Type")
    println("Enter: 1.Admin\t 2.Student\t 3.Faculty")
    val userType= readLine()!!
    //menu for Admin
    if(userType.compareTo("1")==0){
        println("Enter the password")
        var password= readLine()!!
        var wrongPasswordCount=0
        while (password.compareTo(admin.adminPassword)!=0){
            wrongPasswordCount++
            if(wrongPasswordCount>=3){
                println("You have crossed the wrong password limit please try again after 5 minutes")
                return
            }
            println("wrong password!!,Enter the correct password")
            password= readLine()!!
        }
        var adminOptions=0
        while(adminOptions!=9){
            println("Enter:\n1.Show All Books\n2.Add a Book\n3.Remove a Book\n4.Issue a book\n5.Return a book\n6" +
                    ".Renew a book\n7.Search\n8.To change the password\n9.LogOut")
            adminOptions= readLine()!!.toInt()
            when(adminOptions){
                1->l1.printAll()
                2->admin.addBook(l1)
                3->admin.removeBook(l1)
                4->admin.issueBook(l1)
                5->admin.returnBook(l1)
                6->admin.renewBook(l1)
                7->admin.search(l1)
                8->admin.changePassword(0)
                9-> println("You have been Logged out successfully, Thank You!")
                else->println("choose a option from the following:")
            }
        }

    }
    //menu for student
    else if(userType=="2"){
        var option =0
        var wrongOptionCount =0
        var sid=0
        while (option!=1 && option!=2 && option!=3){
            println("Enter: 1.New Student\t 2.Old Student\t 3.Exit")
            option= readLine()!!.toInt()
            when (option) {
                1 -> {
                    sid=admin.addStudent(l1)
                }
                2 -> {
                    println("Enter your student ID")
                    sid = readLine()!!.toInt()
                    var wrongIdCount=0
                    while (!l1.checkIfStudentAlreadyPresent(sid)) {
                        wrongIdCount++
                        if(wrongIdCount>=3) {
                            println("You have reached the wrong id limit please try after 5 minutes")
                            return
                        }
                        println("entered ID is not correct,enter the correct studentID")
                        sid = readLine()!!.toInt()
                    }
                }
                3 -> {
                    println("Thank You!")
                    return
                }
                else -> {
                    wrongOptionCount++
                    if(wrongOptionCount>=3){
                        println("You have reached the wrong input limit, try again after 5 minutes")
                        return
                    }
                    println("Enter the correct option")
                }
            }
        }
        if(l1.checkIfStudentAlreadyPresent(sid)){
            val student=l1.getStudent(sid)
            println("welcome ${student.firstName} ${student.lastName}!")
            var studentOption=0
            while(studentOption!=4){
                println("Enter:\n1.Show all Books\n2.My Books\n3.Search\n4.LogOut")
                studentOption= readLine()!!.toInt()
                when(studentOption){
                    1->l1.printAll()
                    2->l1.myBooks(student.studentID )
                    3->admin.search(l1)
                    4->println("Thank you ${student.firstName} for visiting ${l1.name}!")
                    else->println("choose a option from the following:")
                }
            }
        }
        else{
            println("Student not found!!")
        }
        }
    else if(userType=="3"){
        var option =0
        val adminHelper=Admin()
        var wrongOptionCount =0
        var tid=0
        while (option!=1 && option!=2 && option!=3){
            println("Enter: 1.New Faculty\t 2.Old Faculty\t 3.Exit")
            option= readLine()!!.toInt()
            when (option) {
                1 -> {
                    tid=adminHelper.addFaculty(l1)
                }
                2 -> {
                    println("Enter your student ID")
                    tid = readLine()!!.toInt()
                    var wrongIdCount=0
                    while (!l1.checkIfFacultyAlreadyPresent(tid)) {
                        wrongIdCount++
                        if(wrongIdCount>=3) {
                            println("You have reached the wrong id limit please try after 5 minutes")
                            return
                        }
                        println("entered ID is not correct,enter the correct teacherID")
                        tid = readLine()!!.toInt()
                    }
                }
                3 -> {
                    println("Thank You!")
                    return
                }
                else -> {
                    wrongOptionCount++
                    if(wrongOptionCount>=3){
                        println("You have reached the wrong input limit, try again after 5 minutes")
                        return
                    }
                    println("Enter the correct option")
                }
            }
        }
        if(l1.checkIfFacultyAlreadyPresent(tid)){
            val faculty=l1.getFaculty(tid)
            println("welcome ${faculty.firstName} ${faculty.lastName}!")
            var facultyOption=0
            while(facultyOption!=4){
                println("Enter:\n1.Show all Books\n2.My Books\n3.Seach\n4.LogOut")
                facultyOption= readLine()!!.toInt()
                when(facultyOption){
                    1->l1.printAll()
                    2->l1.myBooks(faculty.teacherID)
                    3->admin.search(l1)
                    4->println("Thank you ${faculty.firstName} for visiting ${l1.name}!")
                    else->println("choose a option from the following:")
                }
            }
        }
        else{
            println("Faculty not found!!")
        }
    }
    else{
        println("You have Entered a wrong user type!")
    }

    }