<%@page import="ucrex.usage.statistics.UCRexStats"%>
<%@page import="ucrex.usage.statistics.db.DataBank"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
DataBank db = UCRexStats.getInstance().getDatabank();
db.open();
String data = db.getUserAndQueryStats().usersAndQueriesAsString();
db.close();
%>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          <%=data%>
        ]);

        var options = {
          title: 'UC Rex Statistics',
          hAxis: {title: 'Month',  titleTextStyle: {color: 'red'}},
          vAxis: {title: 'Number'},
		  seriesType: "bars",
		  series:{1:{type:"line"}}
        };

        var chart = new google.visualization.ComboChart(document.getElementById('chart_1'));
        chart.draw(data, options);
      }
</script>
<div>
	<h2>Total Users and Queries per month</h2>
</div>
<div id="chart_1" style="width: 900px; height: 500px;"></div>