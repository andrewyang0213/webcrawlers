package com.images;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.images.commons.dbutils.DbUtils;
import com.images.commons.dbutils.QueryRunner;
import com.images.commons.dbutils.handlers.ArrayListHandler;

public class DBHelper {
    // <summary>
    // 从db获取一条记录
    // </summary>
    // <param name="status"></param>
    // <returns></returns>
    static String myDriver = "org.gjt.mm.mysql.Driver";
    static String myUrl = "jdbc:mysql://localhost:3306/download_db?useSSL=false";
    static String user = "root";
    static String password = "ubercharge1";

    public static List<ImageMateData> GetImageMateDatasFromDb(int status) {
        List<ImageMateData> awaitingDownload = new ArrayList<ImageMateData>();
        try {
            QueryRunner run = new QueryRunner();
            Connection conn = DriverManager.getConnection(myUrl, user, password);
            String selectQuery = "SELECT * FROM ImageMateData WHERE Status = ?";
            Class.forName(myDriver);

            try {
                List<Object[]> result = run.query(conn, selectQuery, new ArrayListHandler(), status);
                for (Object[] data : result) {
                    ImageMateData imageInfo = new ImageMateData("", "", "");
                    imageInfo.setSourceUri((String) data[1]);
                    imageInfo.setUri((String) data[2]);
                    imageInfo.setDesc((String) data[3]);
                    awaitingDownload.add(imageInfo);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtils.close(conn);
            }

            // String query = "SELECT * FROM download_db.ImageMateData WHERE status = 0;";

            // PreparedStatement preparedStmt = conn.prepareStatement(query);

            // ResultSet result = preparedStmt.executeQuery();

            // conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return awaitingDownload;
    }

    public static Boolean Exists(ImageMateData mateData) {
        Boolean exist = false;
        String imageID = mateData.getHash();
        String existsQuery = "SELECT * FROM download_db.ImageMateData WHERE HashKey = ?";
        try {
            QueryRunner run = new QueryRunner();
            Connection conn = DriverManager.getConnection(myUrl, user, password);

            try {
                List<Object[]> result = run.query(conn, existsQuery, new ArrayListHandler(), imageID);
                if (!result.isEmpty()) {
                    exist = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtils.close(conn);
            }

            // String query = "SELECT * FROM download_db.ImageMateData WHERE hash_key = ?";
            // PreparedStatement preparedStmt = conn.prepareStatement(query);
            // preparedStmt.setString(1, imageID);

            // ResultSet result = preparedStmt.executeQuery();
            // Boolean exist = result.next();

            // conn.close();
            return exist;

        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Boolean Insert(ImageMateData mate) {
        int defaultStatus = 0;
        String uri = mate.getSourceUri();
        String imgUri = mate.getUri();
        String imgDesc = mate.getDesc();
        String hash = mate.getHash();
        String insertQuery = "INSERT IGNORE into ImageMateData(SourceUri, ImageUri, ImageDesc, HashKey, Status) VALUES (?,?,?,?,?)";
        try {
            QueryRunner run = new QueryRunner();
            Connection conn = DriverManager.getConnection(myUrl, user, password);
            Class.forName(myDriver);

            try {
                int inserted = run.update(conn, insertQuery, uri, imgUri, imgDesc, hash, defaultStatus);
                // System.out.println(inserted + " record(s) inserted");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtils.close(conn);
            }

            // String query = "insert ignore into ImageMateData (source_uri, image_uri,
            // image_desc, hash_key, status)"
            // + " values (?, ?, ?, ?, ?)";

            // PreparedStatement preparedStmt = conn.prepareStatement(query);
            // preparedStmt.setString(1, uri);
            // preparedStmt.setString(2, imgUri);
            // preparedStmt.setString(3, imgDesc);
            // preparedStmt.setString(4, hash);
            // preparedStmt.setInt(5, defaultStatus);

            // preparedStmt.execute();

            // conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public static Boolean Update(ImageMateData mateData) {
        String hashCode = mateData.getHash();
        String updateQuery = "UPDATE download_db.ImageMateData set Status = ? WHERE HashKey = ?";
        try {
            QueryRunner run = new QueryRunner();
            Connection conn = DriverManager.getConnection(myUrl, user, password);
            Class.forName(myDriver);

            try {
                int updated = run.update(conn, updateQuery, 1, hashCode);
                // System.out.println(updated + " record(s) updated");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtils.close(conn);
            }

            // // create mysql preparedstatement
            // String query = "update download_db.ImageMateData set status = ? where
            // hash_key = ?";
            // PreparedStatement preparedStmt = conn.prepareStatement(query);
            // preparedStmt.setInt(1, 1);
            // preparedStmt.setString(2, hashCode);

            // // execute preparedstatement
            // preparedStmt.executeUpdate();

            // conn.close();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static int ProgressChecker() {
        int pageNumber = 1;
        String orderQuery = "SELECT SourceUri FROM ImageMateData WHERE Status = 0 ORDER BY ID DESC LIMIT 1;";
        String nullOrderQuery = "SELECT SourceUri FROM ImageMateData ORDER BY ID DESC LIMIT 1;";
        try {
            QueryRunner run = new QueryRunner();
            Connection conn = DriverManager.getConnection(myUrl, user, password);
            Class.forName(myDriver);

            try {
                List<Object[]> ordered = run.query(conn, orderQuery, new ArrayListHandler());
                if (ordered.isEmpty()){
                    ordered = run.query(conn, nullOrderQuery, new ArrayListHandler());
                }
                for (Object[] objects : ordered) {
                    String lastPage = (String) objects[0];
                    String pageNumberString = lastPage.substring(lastPage.lastIndexOf("/") + 1,
                            lastPage.indexOf(".html"));
                    pageNumber = Integer.parseInt(pageNumberString) + pageNumber;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtils.close(conn);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageNumber;
    }
}
