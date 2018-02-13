<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Search Result</title>

    <style type="text/css">
        .tg {
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #ccc;
        }

        .tg td {
            font-family: Arial, sans-serif;
            font-size: 14px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #fff;
        }

        .tg th {
            font-family: Arial, sans-serif;
            font-size: 14px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #f0f0f0;
        }

        .tg .tg-4eph {
            background-color: #f9f9f9
        }
    </style>
</head>
<body>
<a href="<c:url value="/refresh"/>" >Back</a>

<br/>
<br/>

<h1>Search Result</h1>

<c:if test="${!empty listBooks}">
<table class="tg">
    <tr>
        <th width="80">ID</th>
        <th width="120">Title</th>
        <th width="120">Author</th>
        <th width="120">PrintYear</th>
        <th width="100">ISBN</th>
        <th width="500">Description</th>
        <th width="60">AlreadyRead</th>
        <th width="60">Read</th>
        <th width="60">Edit</th>
        <th width="60">Delete</th>
    </tr>

    <c:forEach items="${listBooks}" var="book" >
        <tr>
            <td>${book.id}</td>
            <td><a href="/bookdata/${book.id}" target="_blank">${book.bookTitle}</a></td>
            <td>${book.bookAuthor}</td>
            <td>${book.printYear}</td>
            <td>${book.isbn}</td>
            <td>${book.description}</td>
            <td>${book.alreadyRead}</td>
            <td><a href="<c:url value='/readFromResult/${book.id}'/>">Read</a></td>
            <td><a href="<c:url value='/edit/${book.id}'/>">Edit</a></td>
            <td><a href="<c:url value='/removeFromResult/${book.id}'/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</c:if>
