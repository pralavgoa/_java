<%@page import="ucrex.usage.statistics.UCRexStats"%>
<%@page import="ucrex.usage.statistics.db.DataBank"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
DataBank db = UCRexStats.getInstance().getDatabank();
db.open();
String data = db.getNumberOfObservationsTotal();
db.close();
%>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
      google.load("visualization", "1", {packages:["table"]});
      google.setOnLoadCallback(drawTable);
      function drawTable() {
        var data = google.visualization.arrayToDataTable([
          <%=data%>
        ]);
        var table = new google.visualization.Table(document.getElementById('table_1'));
        table.draw(data, {showRowNumber: true});      }
    </script>
    <div>
    	<h2>Number of observations</h2>
    </div>
	<div id="table_1" style="width: 900px; height: 500px;"></div>