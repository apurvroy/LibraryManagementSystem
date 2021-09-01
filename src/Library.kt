import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Library(val name:String, private val address:String) {
    private val listOfBooks= mutableListOf<BookItem>()
    private val listOfStudents= mutableListOf<Student>()
    private val listOfFaculties= mutableListOf<Faculty>()
    private val listOfIssuedBooks= mutableMapOf<Int,Int>()
    fun addStudentToLibrary(student: Student){
            listOfStudents.add(student)
    }
    fun addFacultyToLibrary(faculty: Faculty){
        listOfFaculties.add(faculty)
    }
    fun checkIfStudentAlreadyPresent(sid:Int):Boolean{
        return listOfStudents.find{it.studentID==sid} in listOfStudents
    }
    fun checkIfFacultyAlreadyPresent(tid:Int):Boolean{
        return listOfFaculties.find { it.teacherID==tid } in listOfFaculties
    }
    fun addBooksToLibrary(newBook:BookItem){
        listOfBooks.add(newBook)
    }
    fun checkIfBookPresent(bid:Int): Boolean {
        return listOfBooks.find { it.BID==bid } in listOfBooks
    }
    fun checkIfBookAvailable(bid:Int,id:Int):Boolean{
        return when(listOfBooks.first { it.BID==bid }.status){
            BookStatus.Available-> true
            BookStatus.ReadOnly->{
                    println("You can read this book only inside the library")
                    false
            }
            BookStatus.Issued->{
                println("Book is currently not available")
                false
            }
            BookStatus.Lost->{println("The Book is Lost")
            false}
            BookStatus.Reserved->{
                return if(checkIfFacultyAlreadyPresent(id)) true
                else {
                    println("this book is only for the faculties")
                    false
                }
            }
            else-> false
        }
    }
    fun deleteBook(bid:Int){
        listOfBooks.remove(listOfBooks.first { it.BID==bid })
        println("Book is removed from the library")
    }
    fun printAll(){
        val format = "| %1$-3s| %2$-30s | %3$-40s| %4$-10s | %5$-10s |\n"
        System.out.format(format,"---","------------------------------","----------------------------------------","----------","----------")
        System.out.format(format,"BID","Title","Author","Type","Status")
        System.out.format(format,"---","------------------------------","----------------------------------------","----------","----------")
        listOfBooks.forEach {
            val status=it.status.toString()
            val type=it.bookType.toString()
            System.out.format(format,it.BID,it.title,it.author,type,status)
        }
        System.out.format(format,"---","------------------------------","----------------------------------------","----------","----------")
    }
    fun myBooks(sid:Int){
        if(listOfIssuedBooks.containsValue(sid)){
            val bookList= mutableListOf<BookItem>()
            for(key in listOfIssuedBooks.keys){
                if(listOfIssuedBooks[key]==sid){
                    bookList.add(listOfBooks.first{it.BID==key})
                }
            }

            val format = "| %1$-3s| %2$-30s | %3$-40s| %4$-10s| %5$-10s|\n"
            System.out.format(format,"---","------------------------------","--------------------","----------","----------")
            System.out.format(format,"BID","Title","Author","Type","Issued on")
            System.out.format(format,"---","------------------------------","--------------------","----------","----------")
            bookList.forEach {
                val type=it.bookType.toString()
                val issueDate=it.issueDate.toString()
                System.out.format(format,it.BID,it.title,it.author,type,issueDate)
            }
            System.out.format(format,"---","------------------------------","--------------------","----------","----------")
        }
        else{
            println("You have no books from $name")
        }
    }
    fun issue(bid:Int,id: Int){
        listOfIssuedBooks.put(bid,id)
        listOfBooks.first{it.BID==bid}.issueDate= LocalDate.now()
        listOfBooks.first{it.BID==bid}.status=BookStatus.Issued
        println("the book is added into your account!")
    }
    fun putBack(bid:Int,id:Int){
        if(listOfIssuedBooks.containsKey(bid)){
            if(checkIfFacultyAlreadyPresent(id)){
                listOfBooks.first {it.BID==bid}.status=BookStatus.Reserved
            }
            else{
                listOfBooks.first{it.BID==bid}.issueDate?.let { calFine(it) }
                listOfBooks.first{it.BID==bid}.issueDate=null
                listOfBooks.first {it.BID==bid}.status=BookStatus.Available
                listOfIssuedBooks.remove(bid)
            }
            println("You have successfully returned the Book")
        }
        else{
            println("Entered book id is incorrect")
        }
    }
    fun renew(bid:Int){
        if(listOfIssuedBooks.containsKey(bid)){
            listOfBooks.first{it.BID==bid}.issueDate?.let { calFine(it) }
            listOfBooks.first{it.BID==bid}.issueDate= LocalDate.now()
            println("the book is successfully renewed!")
        }
        else{
            println("Entered book id is incorrect")
        }
    }
    private fun calFine(issueDate: LocalDate){
        val currDate= LocalDate.now()
        val days=issueDate.until(currDate, ChronoUnit.DAYS)
        val fine=(days-14)*5
        if(days>=14){
            println("You need to submit a fine of $fine rs.")
        }
        else{
            println("You have submitted the book in allocated time No need to submit a fine!")
        }
    }
    fun currentBookCount(sid:Int): Int {
        return listOfIssuedBooks.count { it.value==sid }
    }
    fun getStudent(sid:Int): Student{
        return listOfStudents.first{ it.studentID==sid }
    }
    fun getFaculty(tid:Int):Faculty{
        return listOfFaculties.first{it.teacherID==tid}
    }
//    override fun toString(): String {
//        println("this $name at $address has ${listOfBooks.size} Books")
//        return ""
//    }

    fun searchById() {
        println("Enter Book ID")
        val bid= readLine()!!.toInt()
        val bookList= mutableListOf<BookItem>()
        for(book in listOfBooks){
            if(bid==book.BID){
                bookList.add(book)
            }
        }
        if(bookList.isEmpty()){
            println("Book Not Found!")
        }else{
            val format = "| %1$-3s| %2$-30s | %3$-40s| %4$-10s| %5$-10s|\n"
            System.out.format(format,"---","------------------------------","----------------------------------------","----------","----------")
            System.out.format(format,"BID","Title","Author","Type","Status")
            System.out.format(format,"---","------------------------------","----------------------------------------","----------","----------")
            bookList.forEach {
                val type=it.bookType.toString()
                val status=it.status.toString()
                System.out.format(format,it.BID,it.title,it.author,type,status)
            }
            System.out.format(format,"---","------------------------------","----------------------------------------","----------","----------")
        }
    }

    fun searchByTitle() {
        println("Enter Book Title")
        val title= readLine()!!
        val bookList= mutableListOf<BookItem>()
        for(book in listOfBooks){
            if(title==book.title){
                bookList.add(book)
            }
        }
        if(bookList.isEmpty()){
            println("Book Not Found!")
        }else{
            val format = "| %1$-3s| %2$-30s | %3$-40s| %4$-10s| %5$-10s|\n"
            System.out.format(format,"---","------------------------------","----------------------------------------","----------","----------")
            System.out.format(format,"BID","Title","Author","Type","Status")
            System.out.format(format,"---","------------------------------","----------------------------------------","----------","----------")
            bookList.forEach {
                val type=it.bookType.toString()
                val status=it.status.toString()
                System.out.format(format,it.BID,it.title,it.author,type,status)
            }
            System.out.format(format,"---","------------------------------","----------------------------------------","----------","----------")
        }
    }

    fun searchByAuthor() {
        println("Enter Author's Name")
        val author= readLine()!!
        val bookList= mutableListOf<BookItem>()
        for(book in listOfBooks){
            if(author==book.author){
                bookList.add(book)
            }
        }
        if(bookList.isEmpty()){
            println("Book Not Found!")
        }else{
            val format = "| %1$-3s| %2$-30s | %3$-40s| %4$-10s| %5$-10s|\n"
            System.out.format(format,"---","------------------------------","----------------------------------------","----------","----------")
            System.out.format(format,"BID","Title","Author","Type","Status")
            System.out.format(format,"---","------------------------------","----------------------------------------","----------","----------")
            bookList.forEach {
                val type=it.bookType.toString()
                val status=it.status.toString()
                System.out.format(format,it.BID,it.title,it.author,type,status)
            }
            System.out.format(format,"---","------------------------------","----------------------------------------","----------","----------")
        }
    }

    fun searchByType() {
        val type:BookType
        val bookList= mutableListOf<BookItem>()
        println("Select Type of the book")
        println("Drama\nFiction\nHorror\nRomance\nBiography\nHistoric\nMagazine\nJournal")
        var input= readLine()!!
        input=input.lowercase().replaceFirstChar { it.uppercase() }
        if(input=="Horror"||input=="Drama"||input=="Fiction"||input=="Romance"||input=="Biography"||input
            =="Historic"||input=="Magazine"||input=="Journal") {
            type = BookType.valueOf(input)
            for(book in listOfBooks){
                if(type==book.bookType){
                    bookList.add(book)
                }
            }
            if(bookList.isEmpty()){
                println("Book Not Found!")
            }else{
                val format = "| %1$-3s| %2$-30s | %3$-40s| %4$-10s| %5$-10s|\n"
                System.out.format(format,"---","------------------------------","----------------------------------------","----------","----------")
                System.out.format(format,"BID","Title","Author","Type","Status")
                System.out.format(format,"---","------------------------------","----------------------------------------","----------","----------")
                bookList.forEach {
                    val type=it.bookType.toString()
                    val status=it.status.toString()
                    System.out.format(format,it.BID,it.title,it.author,type,status)
                }
                System.out.format(format,"---","------------------------------","----------------------------------------","----------","----------")
            }
        }else{
            println("YOu have entered a wrong input!")
        }

    }
}
