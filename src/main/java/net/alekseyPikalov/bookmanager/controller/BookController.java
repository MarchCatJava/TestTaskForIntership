package net.alekseyPikalov.bookmanager.controller;

import net.alekseyPikalov.bookmanager.model.Book;
import net.alekseyPikalov.bookmanager.service.BookService;
import net.alekseyPikalov.bookmanager.model.Book;
import net.alekseyPikalov.bookmanager.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {
    private BookService bookService;
    private List<Book> actuallyList ;
    private int counterForObserver = 1;
    private int quantityOfPages = 1;
    private int quantityOfLines = 10;
    private String searchQuery = null;


    @Autowired(required = true)
    @Qualifier(value = "bookService")
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = "/refresh",method = RequestMethod.GET)
    public String refresh(Model model){
        this.counterForObserver = 1;
        this.actuallyList = this.bookService.listBooks();
        setQuantityOfPages();
        model.addAttribute("book", new Book());
        model.addAttribute("listBooks", actuallyList);
        return listBooks(model);
    }


    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public String listBooks(Model model){
        model.addAttribute("book", new Book());

        if (counterForObserver*quantityOfLines < actuallyList.size()) {
            model.addAttribute("listBooks", actuallyList.subList((counterForObserver * quantityOfLines) - quantityOfLines,
                   counterForObserver * quantityOfLines));
       }else
           model.addAttribute("listBooks", actuallyList.subList((counterForObserver * quantityOfLines) - quantityOfLines,actuallyList.size()));

        return "/books";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@ModelAttribute("book") Book book, Model model){
        this.searchQuery = book.getBookTitle();
        preparateSearchResult();
        return searchResult(model);
    }




    @RequestMapping(value = "/books/previous")
    public String prevListBooks(Model model){
        decrCount();
        return "redirect:/books";
    }

    @RequestMapping(value = "/books/following")
    public String followListBooks(Model model){
        intcrCount();
        return "redirect:/books";
    }



    @RequestMapping(value = "/removeFromResult/{id}")
    public String removeFromResultOfSearch(@PathVariable("id") int id, Model model){
        this.bookService.removeBook(id);
        this.actuallyList = this.bookService.listBooks();
        preparateSearchResult();
        return searchResult(model);

    }

    @RequestMapping("/readFromResult/{id}")
    public String readFromResult(@PathVariable("id") int id, Model model){
        this.bookService.readBook(id);
        this.actuallyList = this.bookService.listBooks();
        preparateSearchResult();
        return searchResult(model);
    }


    @RequestMapping(value = "/searchResult")
    public String searchResult(Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("listBooks",this.actuallyList);
        return "/searchResult";
    }

    @RequestMapping(value = "/books/add", method = RequestMethod.POST)
    public String addBook(@ModelAttribute("book") Book book){
        if(book.getId() == 0){
            this.bookService.addBook(book);
        }else {
            this.bookService.updateBook(book);
        }
        this.actuallyList = this.bookService.listBooks();
        return "redirect:/books";
    }

    @RequestMapping("/remove/{id}")
    public String removeBook(@PathVariable("id") int id){
        this.bookService.removeBook(id);
        this.actuallyList = this.bookService.listBooks();
        return "redirect:/books";
    }

    @RequestMapping("/read/{id}")
    public String readBook(@PathVariable("id") int id){
        this.bookService.readBook(id);
        this.actuallyList = this.bookService.listBooks();
        return "redirect:/books";
    }

    @RequestMapping("edit/{id}")
    public String editBook(@PathVariable("id") int id, Model model){
        model.addAttribute("book", this.bookService.getBookById(id));
        model.addAttribute("listBooks", this.bookService.listBooks());

        this.counterForObserver = 1;
        this.actuallyList = this.bookService.listBooks();
        setQuantityOfPages();

        return "editBook";
    }

    @RequestMapping("bookdata/{id}")
    public String bookData(@PathVariable("id") int id, Model model){
        model.addAttribute("book", this.bookService.getBookById(id));

        return "bookdata";
    }

    public int intcrCount(){
        if (counterForObserver < quantityOfPages) counterForObserver++;
        return counterForObserver;
    }

    public int decrCount(){
        if (counterForObserver > 1) counterForObserver--;
        return counterForObserver;
    }

    public void setQuantityOfPages(){
        if (this.actuallyList.size()%this.quantityOfLines > 0)this.quantityOfPages = (this.actuallyList.size() / this.quantityOfLines +1);
        else this.quantityOfPages = actuallyList.size() / this.quantityOfLines;
    }

    public void preparateSearchResult (){
        List<Book> result = new ArrayList<Book>();
        for (Book book1 : this.bookService.listBooks()){
            if (book1.getBookTitle().toLowerCase().contains(this.searchQuery.toLowerCase())) result.add(book1);
        }
        this.actuallyList = result;
        this.counterForObserver = 1;
        setQuantityOfPages();
    }


}
