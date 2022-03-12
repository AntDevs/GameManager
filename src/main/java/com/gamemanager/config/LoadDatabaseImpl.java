package com.gamemanager.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamemanager.beens.Game;
import com.gamemanager.beens.GameJsonMap;
import com.gamemanager.beens.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

@Configuration
public class LoadDatabaseImpl implements LoadDatabase {

    private static final Logger loger = LoggerFactory.getLogger(LoadDatabaseImpl.class);

    @Value( "${json.game.file.name}" )
    private String jsonGameFileName;

    @Bean("Games")
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION)
    public List<Game> getGames() {
        Vector<Game> gameList = new Vector();
        try {
            loger.info("Load Games been from file mask {}" , jsonGameFileName);

            ObjectMapper objectMapper = new ObjectMapper();

            for (int i = 0; i < 10; i++) {
                String fileName = String.format(jsonGameFileName, i);
                loger.info("create Game been from file {}" , fileName);
                File f = new File(fileName);
                if ( ! f.exists() ){
                    loger.info(" file {} not found" , fileName);
                    continue;
                }
                GameJsonMap<GameJsonMap.Question> gameJsonMap =
                        objectMapper.readValue(new File(fileName),
                                new TypeReference<GameJsonMap<GameJsonMap.Question>>(){});
                Game game = new Game();
                gameJsonMap.getResults().parallelStream().forEach(question -> {
                    game.addQuestion(question);
                });
                gameList.add(game);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        loger.info("Games been created size: {}" , gameList.size());
        return gameList;
    }

    @Bean("Users")
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION)
    public List<User> getUsers() {
        Vector<User> UserList = new Vector();
        return UserList;
    }

}
