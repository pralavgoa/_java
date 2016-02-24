package ucrex.usage.statistics.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ucrex.usage.statistics.UCRexStats;
import ucrex.usage.statistics.db.DataBank;

@WebServlet("/download")
public class DownloadFile extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(WebRequestAcceptor.class);
    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PrintWriter out = res.getWriter();
        DataBank db = UCRexStats.getInstance().getDatabank();
        String output = "ERROR";
        try {
            db.open();
            output = db.getAllNumberOfObservations();
            db.close();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        res.setContentType("text/csv");
        res.setHeader("Content-Disposition", "attachment; filename='number_of_observations.csv'");
        out.write(output);
        out.close();
    }
}
