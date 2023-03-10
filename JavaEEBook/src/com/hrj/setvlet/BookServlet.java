package com.hrj.setvlet;

import com.hrj.bean.TBook;
import com.hrj.service.BookService;
import com.hrj.service.impl.BookServiceImpl;
import com.hrj.utils.BaseServlet;
import com.hrj.utils.Page;
import com.hrj.utils.WebUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "BookServlet", value = "/BookServlet")
public class BookServlet extends BaseServlet {

    protected void searchPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name") == null ? "" : request.getParameter("name");
        System.out.println("name = " + name);
        String author = request.getParameter("author") == null ? "" : request.getParameter("author");
        System.out.println("author = " + request.getParameter("auhtor"));
        Integer min = WebUtils.parseInt(request.getParameter("min"), 0);
        Integer max = WebUtils.parseInt(request.getParameter("max"), 0);
        request.setAttribute("name", name);
        request.setAttribute("author", author);
        request.setAttribute("min", request.getParameter("min"));
        request.setAttribute("max", request.getParameter("max"));
        BookService bookService = new BookServiceImpl();
        //1 获取请求的参数 pageNo 和 pageSize
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);//取不到值默认显示第一页
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);//取不到值默认每页显示4条数据
        Page<TBook> page = null;
        try {
            /*if (min != null && max != null && !min.equals("") && !max.equals("")) {
                page = bookService.page(pageNo, pageSize, new BigDecimal(min), new BigDecimal(max));
                page.setUrl("BookServlet?action=searchPage&min=" + min + "&max=" + max + "&");
                request.setAttribute("min", min);
                request.setAttribute("max", max);
                System.out.println("page = " + page);
            } else {
                page = bookService.page(pageNo, pageSize);
                System.out.println("page = " + page);
                page.setUrl("BookServlet?action=searchPage&");
            }*/
            page = bookService.page(pageNo, pageSize, name, author, new BigDecimal(min), new BigDecimal(max));
            page.setUrl("BookServlet?action=searchPage&name=" + name + "&author=" + author + "&min=" + (min == 0 ? "" : min) + "&max=" + (max == 0 ? "" : max) + "&");

            request.setAttribute("page", page);
            request.getRequestDispatcher("/home.jsp").forward(request, response);
//            response.sendRedirect("home.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
//        String id = request.getParameter("id");//获取id
        PrintWriter out = response.getWriter();
        TBook book = new TBook();
        BookService bookService = new BookServiceImpl();
        if (ServletFileUpload.isMultipartContent(request)) {
            //创建FileItemFactory 工厂实现类
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            // 创建用于解析上传数据的工具类ServletFileUpload 类
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            try {
                // 解析上传的数据，得到每一个表单项FileItem
                List<FileItem> list = servletFileUpload.parseRequest(request);
                //循环这6段数据并处理它们
                for (FileItem fileItem : list) {
                    //判断那些是普通表单项,还是上传的文件类型
                    if (fileItem.isFormField()) {
                        //处理普通表单项
                        if ("id".equals(fileItem.getFieldName())) {
                            book.setId(Integer.valueOf(fileItem.getString()));//图书id
                            book = bookService.queryBookById(book.getId());//通过主键id查询book信息
//                            book.setId(Integer.valueOf(id));//图书id
                        } else if ("name".equals(fileItem.getFieldName())) {
                            book.setName(fileItem.getString("utf-8"));//图书名
                        } else if ("author".equals(fileItem.getFieldName())) {
                            book.setAuthor(fileItem.getString("utf-8"));//图书作者
                        } else if ("price".equals(fileItem.getFieldName())) {
                            book.setPrice(new BigDecimal(fileItem.getString()));//图书价格
                        } else if ("sales".equals(fileItem.getFieldName())) {
                            book.setSales(Integer.valueOf(fileItem.getString()));//图书销量
                        } else if ("stock".equals(fileItem.getFieldName())) {
                            book.setStock(Integer.parseInt(fileItem.getString()));//图书库存
                        }
                       /* else if ("oldPath".equals(fileItem.getFieldName())) {
                            book.setImgpath(fileItem.getString());//保存的是图片原地址
                        }*/
                    }
                    //自己写的方法
                    /*else {
                        String imgpath = request.getParameter("imgpath");
                        System.out.println("imgpath = " + imgpath);
                        //处理文件类型(文件上传)
                        String filename = fileItem.getName();//获取文件名
                        System.out.println("filename = " + filename);
                        if (filename != "") {//判断图片是否更新
                            //文件名 = 123.jpg       suffix = .jpg
                            String suffix = filename.substring(filename.lastIndexOf("."));
                            //通过时间毫秒 + 后缀 = 新文件名
                            String newfilename = System.currentTimeMillis() + suffix;

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String format = simpleDateFormat.format(new Date());
                            File file = new File("e:/bookimg/" + format + "/");
                            if (!file.exists()) {//判断要上传的文件目录是否存在
                                file.mkdirs();//创建目录
                            }
                            System.out.println(file.getAbsolutePath());
                            fileItem.write(new File(file, newfilename));//上传图片
                            book.setImgpath("/bookimg/" + format + "/" + newfilename);//图书封面
                            File oldfile = new File("e:" + imgpath);
//                            System.out.println("oldfile = " + oldfile);
//                            System.out.println(oldfile.delete());
                            oldfile.delete();//如果图片更新,把原图片删除
                        } else {
                            book.setImgpath(imgpath);//图书封面
                        }
                    }*/
                    //老师的两种方法
                    else {
                        //处理文件类型(文件上传)
                        String filename = fileItem.getName();//获取文件名
                        if (!filename.equals("")) {
                            //文件名 = 123.jpg       suffix = .jpg
                            String suffix = filename.substring(filename.lastIndexOf("."));
                            //通过时间毫秒 + 后缀 = 新文件名
                            String newfilename = System.currentTimeMillis() + suffix;


                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String format = simpleDateFormat.format(new Date());
                            File file = new File("e:/bookimg/" + format + "/");
                            if (!file.exists()) {//判断要上传的文件目录是否存在
                                file.mkdirs();//创建目录
                            }
                            System.out.println(file.getAbsolutePath());
                            fileItem.write(new File(file, newfilename));//上传图片
                            String path = "e:/" + book.getImgpath();
                            File temp = new File(path);
                            temp.delete();
                            book.setImgpath("/bookimg/" + format + "/" + newfilename);//图书封面
                        }
                    }
                }
                System.out.println("book = " + book);
                bookService.updateBook(book);//将图片信息保存到数据库
//                request.getRequestDispatcher("/BookServlet?action=list").forward(request,response);
                String pageNo = request.getParameter("pageNo");
                System.out.println("pageNo = " + pageNo);
                response.sendRedirect("BookServlet?action=page&pageNo=" + pageNo);
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            out.println("不是多段数据..无法上传文件!");
        }
    }


    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String id = request.getParameter("id");
        Integer id = WebUtils.parseInt(request.getParameter("id"), 0);
        BookService bookService = new BookServiceImpl();
        try {
            TBook book = bookService.queryBookById(id);
            String path = "e:" + book.getImgpath();
            File file = new File(path);
            file.delete();
            bookService.deleteBookById(id);
            String pageNo = request.getParameter("pageNo");
//            request.getRequestDispatcher("/BookServlet?action=page&pageNo="+pageNo).forward(request,response);
            response.sendRedirect("BookServlet?action=page&pageNo=" + pageNo);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    protected void queryById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String pageNo = request.getParameter("pageNo");
        System.out.println("pageNo1 = " + pageNo);
        BookService bookService = new BookServiceImpl();
        try {
            TBook book = bookService.queryBookById(Integer.valueOf(id));
            request.setAttribute("book", book);
            request.setAttribute("pageNo", pageNo);
            request.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(request, response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        //1 获取请求的参数 pageNo 和 pageSize
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);//取不到值默认显示第一页
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);//取不到值默认每页显示4条数据

        try {
            //List<TBook> bookList = bookService.queryBooks();
            Page<TBook> page = bookService.page(pageNo, pageSize);
            page.setUrl("BookServlet?action=page&");
            request.setAttribute("page", page);
            request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request, response);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        TBook book = new TBook();
        BookService bookService = new BookServiceImpl();
        if (ServletFileUpload.isMultipartContent(request)) {
            //创建FileItemFactory 工厂实现类
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            // 创建用于解析上传数据的工具类ServletFileUpload 类
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            try {
                // 解析上传的数据，得到每一个表单项FileItem
                List<FileItem> list = servletFileUpload.parseRequest(request);
                for (FileItem fileItem : list) {
                    //判断那些是普通表单项,还是上传的文件类型
                    if (fileItem.isFormField()) {
                        //处理普通表单项
                        if ("name".equals(fileItem.getFieldName())) {
                            book.setName(fileItem.getString("utf-8"));//图书名
                        } else if ("author".equals(fileItem.getFieldName())) {
                            book.setAuthor(fileItem.getString("utf-8"));//图书作者
                        } else if ("price".equals(fileItem.getFieldName())) {
                            book.setPrice(new BigDecimal(fileItem.getString()));//图书价格
                        } else if ("sales".equals(fileItem.getFieldName())) {
                            book.setSales(Integer.valueOf(fileItem.getString()));//图书销量
                        } else if ("stock".equals(fileItem.getFieldName())) {
                            book.setStock(Integer.parseInt(fileItem.getString()));//图书库存
                        }
                    } else {
                        //处理文件类型(文件上传)
                        String filename = fileItem.getName();//获取文件名
                        //文件名 = 123.jpg       suffix = .jpg
                        String suffix = filename.substring(filename.lastIndexOf("."));
                        //通过时间毫秒 + 后缀 = 新文件名
                        String newfilename = System.currentTimeMillis() + suffix;


                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String format = simpleDateFormat.format(new Date());
                        File file = new File("e:/bookimg/" + format + "/");
                        if (!file.exists()) {//判断要上传的文件目录是否存在
                            file.mkdirs();//创建目录
                        }
                        System.out.println(file.getAbsolutePath());
                        fileItem.write(new File(file, newfilename));//上传图片
                        book.setImgpath("/bookimg/" + format + "/" + newfilename);//图书封面
                    }
                }
                bookService.addBook(book);//将图片信息保存到数据库
//                request.getRequestDispatcher("/BookServlet?action=list").forward(request,response);
//                request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request,response);
                response.sendRedirect("BookServlet?action=page");
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            out.println("不是多段数据..无法上传文件!");
        }
    }
}
