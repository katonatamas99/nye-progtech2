package elsovedesre.src.main.torpedo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import elsovedesre.src.main.torpedo.model.BoxDescription;
import elsovedesre.src.main.torpedo.model.MapVO;
import elsovedesre.src.main.torpedo.service.exception.MapParseException;
import elsovedesre.src.main.torpedo.service.exception.MapReadException;
import elsovedesre.src.main.torpedo.service.exception.MapValidationException;
import elsovedesre.src.main.torpedo.service.map.parser.MapParser;
import elsovedesre.src.main.torpedo.service.map.reader.MapReader;
import elsovedesre.src.main.torpedo.service.map.reader.impl.BufferedReaderMapReader;
import elsovedesre.src.main.torpedo.service.map.validator.MapValidator;
import elsovedesre.src.main.torpedo.service.map.validator.impl.MapByBoxValidator;
import elsovedesre.src.main.torpedo.service.map.validator.impl.MapByColumnValidator;
import elsovedesre.src.main.torpedo.service.map.validator.impl.MapByRowValidator;
import elsovedesre.src.main.torpedo.service.util.CollectionUtil;
import elsovedesre.src.main.torpedo.service.util.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        LOGGER.trace("trace");
        LOGGER.debug("debug");
        LOGGER.info("info");
        LOGGER.warn("warn");
        LOGGER.error("error");

        int[][] map = {
                {0, 1},
                {2, 3}
        };
        boolean[][] fixed = {
                {false, true},
                {true, true}
        };
        MapVO mapVO = new MapVO(2, 2, map, fixed);

        System.out.println(mapVO);

        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("map/beginner.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        MapReader mapReader = new BufferedReaderMapReader(bufferedReader);
        try {
            List<String> strings = mapReader.readMap();
            System.out.println(strings);

            MapParser mapParser = new MapParser(9, 9);
            MapVO mapVO1 = mapParser.parseMap(strings);
            System.out.println(mapVO1);

            CollectionUtil collectionUtil = new CollectionUtil();
            MapUtil mapUtil = new MapUtil();

            MapValidator mapByRowValidator = new MapByRowValidator(collectionUtil, mapUtil);
            MapValidator mapByColumnValidator = new MapByColumnValidator(collectionUtil, mapUtil);
            MapValidator mapByBoxValidator = new MapByBoxValidator(collectionUtil, mapUtil, BoxDescription.BOX_DESCRIPTION_LIST);

            mapByRowValidator.validate(mapVO1);
            mapByColumnValidator.validate(mapVO1);
            mapByBoxValidator.validate(mapVO1);
        } catch (MapReadException e) {
            e.printStackTrace();
        } catch (MapParseException e) {
            e.printStackTrace();
        } catch (MapValidationException e) {
            e.printStackTrace();
        }


}