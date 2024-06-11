<%-- sidebar.jsp --%>
<%@page import="com.amstech.retail.dto.UserDTO"%>
<%
UserDTO auth = (UserDTO) session.getAttribute("auth");
if (auth != null) {
%>

<nav class="main-menu">
    <ul>
        <li>
            <a href="home.jsp" class="nav-item">
                <i class="fa fa-home nav-icon"></i>
                <span class="nav-text">Home</span>
            </a>
        </li>
        <li>
            <form action="user" method="get">
                <input type="hidden" name="task" value="findById">
                <input type="hidden" name="id" value="<%=auth.getId()%>">
                <button type="submit" class="nav-item">
                    <i class="fa fa-user nav-icon"></i>
                    <span class="nav-text">Edit Profile</span>
                </button>
            </form>
        </li>
        <li>
            <form action="order" method="get">
                <input type="hidden" name="task" value="findAllOrdersByStoreId">
                <input type="hidden" name="id" value="<%=auth.getId()%>">
                <button type="submit" class="nav-item">
                    <i class="fa fa-history nav-icon"></i>
                    <span class="nav-text">Order History</span>
                </button>
            </form>
        </li>
        <li>
            <a href="item.jsp" class="nav-item">
                <i class="fa fa-plus nav-icon"></i>
                <span class="nav-text">Add Item</span>
            </a>
        </li>
        <li>
            <form action="items" method="get">
                <input type="hidden" name="task" value="findAllItems">
                <input type="hidden" name="id" value="<%=auth.getId()%>">
                <button type="submit" class="nav-item">
                    <i class="fa fa-search nav-icon"></i>
                    <span class="nav-text">Find All Items</span>
                </button>
            </form>
        </li>
        <li>
            <a href="query.jsp" class="nav-item">
                <i class="fa fa-question nav-icon"></i>
                <span class="nav-text">Add Query</span>
            </a>
        </li>
    </ul>
    <ul class="logout">
        <li>
            <form action="logout" method="post">
                <button type="submit" class="nav-item">
                    <i class="fa fa-power-off nav-icon"></i>
                    <span class="nav-text">Logout</span>
                </button>
            </form>
        </li>
    </ul>
</nav>


<%
} else {
response.sendRedirect("login.jsp"); // Redirect to login page if auth is null
}
%>

