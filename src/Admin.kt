class Admin(firstName: String="Robin", lastName: String="Singh"):Person(firstName, lastName){
    var adminPassword="Admin"
    fun addBook(library: Library){
        val type:BookType
        val title:String
        val author:String
        val status:BookStatus
        println("Enter book details:")
        println("Enter Book ID")
        val bid:Int = readLine()!!.toInt()
        if(library.checkIfBookPresent(bid)){
            println("this book is already available in library!")
        }
        else{
            println("Select Type of the book")
            println("Drama\nFiction\nHorror\nRomance\nBiography\nHistoric\nMagazine\nJournal")
            var input= readLine()!!
            input=input.lowercase().replaceFirstChar { it.uppercase() }
            if(input=="Horror"||input=="Drama"||input=="Fiction"||input=="Romance"||input=="Biography"||input
                =="Historic"||input=="Magazine"||input=="Journal"){
                type= BookType.valueOf(input)
                println("Enter Title of the Book")
                title= readLine()!!.lowercase()
                println("Enter author name:")
                author= readLine()!!.lowercase()
                if(type==BookType.Journal||type==BookType.Magazine)
                    status=BookStatus.ReadOnly
                else
                    status=BookStatus.Available
                val newBook=BookItem(bid,type,status,title,author)
                library.addBooksToLibrary(newBook)
                println(" ${newBook.title} added into the library!")
            }
            else{
                println("You have Entered a wrong input!")
            }
        }
    }
    fun removeBook(library: Library){
        println("Enter the Book id")
        val id= readLine()!!.toInt()
        if(library.checkIfBookPresent(id)){
            library.deleteBook(id)
        }
        else{
            println("this book is not in the library")
        }
    }
    fun issueBook(library: Library){
        println("Enter the Book ID")
        val bid = readLine()!!.toInt()
        println("Enter the person id")
        val id= readLine()!!.toInt()
        if(library.checkIfBookPresent(bid)){
            if(library.checkIfBookAvailable(bid,id)){
                if(library.checkIfStudentAlreadyPresent(id)){
                    if(library.currentBookCount(id)<=2){
                        library.issue(bid,id)
                    }
                    else{
                        println("You already have 3 book from ${library.name},you need to return first!")
                    }

                }
                else if(library.checkIfFacultyAlreadyPresent(id)){
                    library.issue(bid,id)
                }
                else{
                    println("Entered ID is incorrect")
                }

            }
        }
        else{
            println("this book is not in the Library")
        }
    }
    fun returnBook(library: Library){
        println("Enter the person ID")
        val id= readLine()!!.toInt()
        if(library.checkIfStudentAlreadyPresent(id)) {
            library.myBooks(id)
            if(library.currentBookCount(id)==0){
                return
            }
            println("Enter the book id which you want to return")
            val bid= readLine()!!.toInt()
            library.putBack(bid,id)
        }
        else if(library.checkIfFacultyAlreadyPresent(id)){
            library.myBooks(id)
            if(library.currentBookCount(id)==0){
                println("You have no books from ${library.name}")
                return
            }
            println("Enter the book id which you want to return")
            val bid= readLine()!!.toInt()
            library.putBack(bid,id)
        }
        else{
            println("Entered ID is incorrect")
        }

    }
    fun renewBook(library: Library){
       println("Enter the student ID")
        val sid= readLine()!!.toInt()
        if(library.checkIfStudentAlreadyPresent(sid)){
            library.myBooks(sid)
            if(library.currentBookCount(sid)==0){
                return
            }
            println("Enter the book id which you want to renew:")
            val bid= readLine()!!.toInt()
            library.renew(bid)
        }
        else{
            println("Entered student ID is incorrect")
        }

    }
    fun search(library: Library){
        println("Enter your search type:\n1.By Book ID\n2.By Book Title\n3.By Author Name\n4.By Book Type")
        val option= readLine()!!.toInt()
        when(option){
            1->library.searchById()
            2->library.searchByTitle()
            3->library.searchByAuthor()
            4->library.searchByType()
            else->println("You have entered a wrong input!")
        }
    }
    fun changePassword(count:Int){
        if(count>=3){
            println("You have crossed the wrong password limit please try again after 5 minutes ")
            return
        }
        println("Enter old password")
        var password= readLine()!!
        if(password==adminPassword){
            println("Enter new password")
            password= readLine()!!
            adminPassword=password
        }
        else{
            println("Entered Password is not correct")
        }

    }
    //TODO
    fun addStudent(library: Library):Int{
        println("Enter your student ID")
        val id:Int = readLine()!!.toInt()
        if(library.checkIfStudentAlreadyPresent(id)){
            println("Already Added")
        }
        else{
            println("Enter you first Name")
            val firstName:String = readLine()!!
            println("Enter your last Name")
            val lastName:String = readLine()!!
            val student=Student(id,firstName,lastName)
            library.addStudentToLibrary(student)
        }
        return id

    }
    fun addFaculty(library: Library):Int{
        println("Enter your teacher ID")
        val id:Int = readLine()!!.toInt()
        if(library.checkIfFacultyAlreadyPresent(id)){
            println("Already Added")
        }
        else{
            println("Enter your first Name")
            val firstName:String = readLine()!!
            println("Enter your last Name")
            val lastName:String = readLine()!!
            val faculty=Faculty(id,firstName,lastName)
            library.addFacultyToLibrary(faculty)
        }
        return id
    }
}