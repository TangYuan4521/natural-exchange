<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
    <head>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tableAdv.css"/>" />
        <title>Объявления</title>
    </head>
    
    <body>
        <h1>Меню</h1>
        <div class="page-wrapper">
            <div class='advertisement-table'>
                <table>
                    <thead>
                        <tr>
                            <!--th>
                                Автор
                            </th-->
                            <th>
                                 <a href='<c:url value="${titleSortingUrl}"/>'>Заголовок</a>
                            </th>
                            <th>
                                 <a href='<c:url value="${dateSortingUrl}"/>'>Дата</a>
                            </th>
                            <th>
                                Текст
                            </th>
                            <th>
                                Фото
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${advertisements}" var="advertisement">
                        <tr>
                            <td><c:out value="${advertisement.title}"/></td>
                            <td><c:out value="${advertisement.createdDateFormat}"/></td>
                            <td><c:out value="${advertisement.text}"/></td>
                            <td><img src='<c:url value="/resources/images/${advertisement.photoFile}"/>'/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                
            </div>
            
            <div class="category-table">
                <table>
                    <thead>
                        <tr>
                            <th>Категории</th>
                        </tr>
                    </thead>
                    <c:forEach items="${categories}" var="category">
                        <tr>
                            <td><c:out value="${category}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
		<!--div>
		    <select name="pageSizeSelect" >
			    <option value = "2" selected>2</option>
			    <option value = "10">10</option>
			    <option value = "25">25 </option>
		    </select>
			<c:url var="url" value="list.html">
                <c:param name="pageSize" value='${1}'/>
            </c:url>
			<submit = button '${url}'>выбрать</>
		</div-->
    </body>
</html>
