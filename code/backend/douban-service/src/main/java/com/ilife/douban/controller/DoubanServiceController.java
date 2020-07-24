package com.ilife.douban.controller;

import com.ilife.douban.entity.Movie;
import com.ilife.douban.service.UserService;
import com.ilife.douban.entity.Book;
import com.ilife.douban.entity.User;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController

@Api(tags = {"Douban Service Controller"}, description = "Everything about auth & CRUD of iLife User")
public class DoubanServiceController {
    @Autowired
    private UserService userService;

    @ApiOperation(notes = "Get user info by userID", value = "get user info by id", httpMethod = "GET")
    @RequestMapping(path = "/douban/getById")
    public User getUserById(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestParam("userId") String uid) {
        System.out.println("********** getUserById **********");
        return userService.findById(uid);
    }

    @ApiResponses({
            @ApiResponse(code = 501, message = "user Id not exists"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname", value = "the nickname of the iLife user"),
    }
    )
    @ApiOperation(notes = "Delete one douban user By user Id", value = "delete one user", httpMethod = "POST")
    @RequestMapping(path = "/douban/delById")
    public ResponseEntity<?> deleteById(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestBody Map<String, String> params) {
        String id = params.get("userId");
        System.out.println("********** deleteById **********");
        return userService.deleteById(id);
    }

    @ApiOperation(notes = "get douban user's read list By user Id", value = "get user's books", httpMethod = "GET")
    @RequestMapping(path = "/douban/getBooks")
    public List<Book> getBooks(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestParam("userId") String uid) {
        System.out.println("********** getBooks **********");
        return userService.getBooksById(uid);
    }

    @ApiOperation(notes = "get douban user's watch list By user Id", value = "get user's movies", httpMethod = "GET")
    @RequestMapping(path = "/douban/getMovies")
    public List<Movie> getMovies(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestParam("userId") String uid) {
        System.out.println("********** getMovies **********");
        return userService.getMoviesById(uid);
    }
}
