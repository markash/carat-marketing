package za.co.yellowfire.sab;

import lombok.extern.slf4j.Slf4j;
import org.jooq.SQLDialect;
import org.modelmapper.ModelMapper;
import za.co.yellowfire.sab.db.RecordValueReader;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.sql.DataSource;

@Named @Slf4j @Singleton
public class Application {

    @Resource(name = "jdbc/carat")
    private DataSource dataSource;

    private ModelMapper modelMapper;

    @Produces
    public SQLDialect getSQLDialect() {
        return SQLDialect.POSTGRES;
    }

    @Produces
    public ModelMapper getModelMapper() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
            modelMapper.getConfiguration().addValueReader(new RecordValueReader());
        }
        return modelMapper;
    }
}
