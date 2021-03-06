package com.ilife.douban.controller;

import com.alibaba.fastjson.JSONObject;
import com.ilife.douban.entity.*;
import com.ilife.douban.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

@CrossOrigin("*")
@RestController
@Api(tags = {"Douban Service Controller"}, description = "Everything about auth & CRUD of iLife User")
public class DoubanServiceController {
    @Autowired
    private UserService userService;

    @ApiOperation(notes = "Get user info by userID", value = "get user info by id", httpMethod = "GET")
    @GetMapping(path = "/douban/getById")
    @PreAuthorize("hasRole('ROLE_USER')")
    public User getUserById(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestParam("userId") String uid) {
        System.out.println("********** getUserById **********");
        return userService.findById(uid);
    }

    @ApiResponses({
            @ApiResponse(code = 501, message = "user Id not exists"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname", value = "the nickname of the douban user"),
    }
    )
    @ApiOperation(notes = "Delete one douban user By user Id", value = "delete one user", httpMethod = "POST")
    @PostMapping(path = "/douban/delById")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteById(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestBody Map<String, String> params) {
        String id = params.get("userId");
        System.out.println("********** deleteById **********");
        return userService.deleteById(id);
    }

    @ApiOperation(notes = "get douban user's read list By user Id", value = "get user's books", httpMethod = "GET")
    @GetMapping(path = "/douban/getBooks")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Book> getBooks(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestParam("userId") String uid) {
        System.out.println("********** getBooks **********");
        return userService.getBooksById(uid);
    }

    @ApiOperation(notes = "get douban user's watch list By user Id", value = "get user's movies", httpMethod = "GET")
    @GetMapping(path = "/douban/getMovies")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Movie> getMovies(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestParam("userId") String uid) {
        System.out.println("********** getMovies **********");
        return userService.getMoviesById(uid);
    }

    @ApiResponses({
            @ApiResponse(code = 501, message = "user Id not exists"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "the ID of the douban user"),
    }
    )
    @ApiOperation(notes = "Delete Books By user Id", value = "delete books", httpMethod = "POST")
    @PostMapping(path = "/douban/delBooks")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteBooks(@RequestBody Map<String, String> params) {
        String id = params.get("userId");
        System.out.println("********** deleteBooks **********");
        return userService.deleteBooks(id);
    }

    @ApiResponses({
            @ApiResponse(code = 501, message = "user Id not exists"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "the ID of the douban user"),
    }
    )
    @ApiOperation(notes = "Delete Books By user Id", value = "delete movies", httpMethod = "POST")
    @PostMapping(path = "/douban/delMovies")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteMovies(@RequestBody Map<String, String> params) {
        String id = params.get("userId");
        System.out.println("********** deleteMovies **********");
        return userService.deleteMovies(id);
    }

    @ApiOperation(notes = "Get book Statistics by userID", value = "get book statistics", httpMethod = "GET")
    @GetMapping(path = "/douban/getBookStats")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public BookStats getBookStats(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestParam("userId") String uid) {
        System.out.println("********** getBookStats **********");
        return userService.getBookStats(uid);
    }

    @ApiOperation(notes = "Get movie Statistics by userID", value = "get movie statistics", httpMethod = "GET")
    @GetMapping(path = "/douban/getMovieStats")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public MovieStats getMovieStats(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestParam("userId") String uid) {
        System.out.println("********** getMovieStats **********");
        return userService.getMovieStats(uid);
    }

    @ApiOperation(notes = "Get Recommendation parameter by userID", value = "get movie recommendation", httpMethod = "GET")
    @GetMapping(path = "/douban/getRcmd")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public Reference getRefParameter(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestParam("userId") String uid) {
        System.out.println("********** getRecommendation**********");
        return userService.getRcmd(uid);
    }

    @ApiOperation(notes = "Get Stored Recommendation by userID", value = "get recommendation", httpMethod = "GET")
    @GetMapping(path = "/douban/getStoredRcmd")
 //   @PreAuthorize("hasRole('ROLE_USER')")
    public Recommendation getStoredRcmd(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestParam("userId") String uid) {
        System.out.println("********** getRecommendation**********");
        return userService.getStoredRcmd(uid);
    }

    @ApiOperation(notes = "Get Stored Recommendation by userID", value = "get recommendation", httpMethod = "POST")
    @PostMapping(path = "/douban/saveRcmd")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public void saveRcmd(@RequestBody JSONObject object) {
//        JSONObject object = JSON.parseObject(params.get("rcmd"));
        System.out.println("********** saveRcmd **********");
        JSONObject rcmd = object.getJSONObject("rcmd");
        Recommendation recommendation
                 =new Recommendation(rcmd.getString("id"),rcmd.getString("actors_list_movie"),rcmd.getString("author_book"),parseInt(rcmd.getString("hot_book")),rcmd.getString("introduction_book"),rcmd.getString("introduction_movie"),rcmd.getString("picture_book"),rcmd.getString("picture_movie"),rcmd.getString("price_book"),parseDouble(rcmd.getString("rate_book")),parseDouble(rcmd.getString("rate_movie")),rcmd.getString("title_book"),rcmd.getString("type_movie"),rcmd.getString("title_movie"),rcmd.getString("url_book"),rcmd.getString("url_movie"));
        userService.saveRcmd(recommendation);
    }
}
