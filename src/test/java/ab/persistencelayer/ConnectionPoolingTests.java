package ab.persistencelayer;

import ab.persistencelayer.configuration.HikariDataSourceConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ConnectionPoolingTests {


    @Test
    void getConnection(){
        long start= 0, end=0;
        long memory1 = MemoryUtils.getMemoryUse();
        List<Connection> connections = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            try{
                start = System.nanoTime();
                Connection connection = HikariDataSourceConfig.getConnection();
                end = System.nanoTime();
                connections.add(connection);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            System.out.println((end-start));

        }
        long memory2 = MemoryUtils.getMemoryUse();
        System.out.println(memory2-memory1);

        connections.forEach(connection -> {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        for (int i = 0; i < 5; i++) {
            try{
                start = System.nanoTime();
                Connection connection = HikariDataSourceConfig.getConnection();
                end = System.nanoTime();
                connections.add(connection);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            System.out.println((end-start));

        }

//        try{
//            Thread.sleep(30000);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

    }
}
