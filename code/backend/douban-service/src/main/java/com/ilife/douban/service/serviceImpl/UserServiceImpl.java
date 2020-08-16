package com.ilife.douban.service.serviceImpl;

import com.ilife.douban.dao.MovieDao;
import com.ilife.douban.entity.*;
import com.ilife.douban.service.UserService;
import com.ilife.douban.dao.BookDao;
import com.ilife.douban.dao.UserDao;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;


@Service
public class UserServiceImpl implements UserService {
    private static int[] typeRecord=new int[15];
    private static String[] refList={"剧情","爱情","纪录片","传记","战争","动画","奇幻","冒险","动作","古装","歌舞","惊悚","悬疑","恐怖","犯罪"};
    @Autowired
    private UserDao userDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private MovieDao movieDao;

    @Override
    public User findById(String id) {
        return userDao.findById(id);
    }

    @Override
    public ResponseEntity<?> deleteById(String id) {
        if (userDao.findById(id) == null)
            return ResponseEntity.status(501).body("User not exists");
        userDao.deleteById(id);
        return ResponseEntity.ok().body("successfully delete user " + id);
    }

    @Override
    public MovieStats getRcmd(String uid){
        MovieStats movieStats = getMovieStats(uid);
        BookStats bookStats = getBookStats(uid);

        return null;
    }
    @Override
    public List<Book> getBooksById(String uid) {
        return bookDao.findById(uid);
    }

    @Override
    public List<Movie> getMoviesById(String uid) {
        return movieDao.findById(uid);
    }

    @Override
    public ResponseEntity<?> deleteBooks(String uid) {
        User user = userDao.findById(uid);
        if (user == null)
            return ResponseEntity.status(501).body("user not exists");
        bookDao.DeleteAllById(uid);
        return ResponseEntity.ok().body("successfully delete");
    }

    @Override
    public ResponseEntity<?> deleteMovies(String uid) {
        User user = userDao.findById(uid);
        if (user == null)
            return ResponseEntity.status(501).body("user not exists");
        movieDao.DeleteAllById(uid);
        return ResponseEntity.ok().body("successfully delete");
    }
    @Override
    public MovieStats getMovieStats(String uid) {
        List<Movie> movieList = movieDao.findById(uid);
        float avgRanking = 0, maxRanking = 0, avgHot = 0, maxHot = 0, allRanking = 0, allHot = 0, minHot = 9999999, minRanking=9999999;
        if (movieList.size() == 0) return new MovieStats(0,0, 0, null, 0, null, 0, 0, null, 0, null, 0, "0", "0");
        Movie maxRankingmovie = movieList.get(0);
        Movie maxHotmovie = movieList.get(0);
        Movie minHotmovie = movieList.get(0);
        Movie minRankingmovie = movieList.get(0);
        String preLanguage = movieList.get(0).getLanguage();
        for (Movie movie: movieList) {
            if(!movie.getType().trim().equals(""))
                parseType(movie.getType());
            allHot += movie.getHot();
            if (movie.getHot() >= maxHot) {
                maxHot = movie.getHot();
                maxHotmovie = movie;
            }
            allRanking += movie.getRanking();
            if (movie.getRanking() >= maxRanking) {
                maxRanking = movie.getRanking();
                maxRankingmovie = movie;
            }
            if (movie.getHot() <= minHot) {
                minHot = movie.getHot();
                minHotmovie = movie;
            }
            if (movie.getRanking() <= minRanking) {
                minRanking = movie.getRanking();
                minRankingmovie = movie;
            }
        }
        avgHot = allHot / movieList.size();
        avgRanking = allRanking / movieList.size();
        for (int i = 0; i < movieList.size(); ++i) {
            boolean flag = false;
            for (int j = 0; j < movieList.size() - i - 1; ++j) {
                String previous = movieList.get(j).getLanguage();
                String next = movieList.get(j + 1).getLanguage();
                if (stringCompare(previous, next) > 0) {
                    flag = true;
                    Movie tmpWeibo = movieList.get(j);
                    movieList.set(j, movieList.get(j + 1));
                    movieList.set(j + 1, tmpWeibo);
                }
            }
            if (!flag) break;
        }
        int _cur = 0;
        int maxCount = 0;
        String _curDay = preLanguage;
        String _lastDay = preLanguage;
        for (Movie movie : movieList) {
            _curDay = movie.getLanguage();
            if (_curDay.equals(_lastDay)) {
                _cur++;
                if (_cur > maxCount) {
                    maxCount = _cur;
                    preLanguage = _curDay;
                }
            } else {
                _cur = 1;
            }
            _lastDay = _curDay;
        }
        int max=0;
        String preType="";
        for(int i=1;i<15;++i){
            if(typeRecord[i]>max){
                preType=refList[i];
                max=typeRecord[i];
            }
        }

        return new MovieStats(0,avgRanking,maxRanking,maxRankingmovie,minRanking,minRankingmovie,avgHot,maxHot,maxHotmovie,minHot,minHotmovie,movieList.size(),preLanguage,preType);
    }
    @Override
    public BookStats getBookStats(String uid) {
        Integer preferHot=0;
        int[] preferHotList = new int[4];
        List<Book> bookList = bookDao.findById(uid);
        float avgPrice = 0, maxPrice = 0, avgRanking = 0, maxRanking = 0, avgHot = 0, maxHot = 0, allRanking = 0, allHot = 0, minHot = 9999999, allPrice = 0;
        ;
        if (bookList.size() == 0) return new BookStats(0,0, 0, null, 0, 0, null, 0, 0, null, 0, null, 0, "0");
        Book maxPriceBook = bookList.get(0);
        Book maxRankingBook = bookList.get(0);
        Book maxHotBook = bookList.get(0);
        Book minHotBook = bookList.get(0);
        String preAuthor = bookList.get(0).getAuthor();
        for (Book book : bookList) {
            float price = parseFloat(book.getPrice().replace('元', ' ').replace("NT$"," ").trim());
            allPrice += price;
            allRanking += book.getRanking();
            allHot += book.getHot();
            if (book.getHot() >= maxHot) {
                maxHot = book.getHot();
                maxHotBook = book;
            }
            if(book.getHot()<1000){
                preferHotList[0]++;
            }else if(book.getHot()>=1000 && book.getHot()<10000){
                preferHotList[1]++;
            }else if(book.getHot()>=10000 && book.getHot()<50000){
                preferHotList[2]++;
            }else if(book.getHot()>=50000){
                preferHotList[3]++;
            }
            if (book.getRanking() >= maxRanking) {
                maxRanking = book.getRanking();
                maxRankingBook = book;
            }
            if (price >= maxPrice) {
                maxPrice = price;
                maxPriceBook = book;
            }
            if (book.getHot() <= minHot) {
                minHot = book.getHot();
                minHotBook = book;
            }
        }
        if(preferHotList[0]>preferHotList[1]&&preferHotList[0]>preferHotList[2]&&preferHotList[0]>preferHotList[3]){
            preferHot=0;
        }
        if(preferHotList[1]>preferHotList[0]&&preferHotList[1]>preferHotList[2]&&preferHotList[1]>preferHotList[3]){
            preferHot=1;
        }
        if(preferHotList[2]>preferHotList[1]&&preferHotList[2]>preferHotList[0]&&preferHotList[2]>preferHotList[3]){
            preferHot=2;
        }
        if(preferHotList[3]>preferHotList[1]&&preferHotList[3]>preferHotList[2]&&preferHotList[3]>preferHotList[0]){
            preferHot=3;
        }
        avgHot = allHot / bookList.size();
        avgPrice = allPrice / bookList.size();
        avgRanking = allRanking / bookList.size();
        for (int i = 0; i < bookList.size(); ++i) {
            boolean flag = false;
            for (int j = 0; j < bookList.size() - i - 1; ++j) {
                String previous = bookList.get(j).getAuthor();
                String next = bookList.get(j + 1).getAuthor();
                if (stringCompare(previous, next) > 0) {
                    flag = true;
                    Book tmpWeibo = bookList.get(j);
                    bookList.set(j, bookList.get(j + 1));
                    bookList.set(j + 1, tmpWeibo);
                }
            }
            if (!flag) break;
        }
        int _cur = 0;
        int maxCount = 0;
        String _curDay = preAuthor;
        String _lastDay = preAuthor;
        for (Book book : bookList) {
            _curDay = book.getAuthor();
            if (_curDay.equals(_lastDay)) {
                _cur++;
                if (_cur > maxCount) {
                    maxCount = _cur;
                    preAuthor = _curDay;
                }
            } else {
                _cur = 1;
            }
            _lastDay = _curDay;
        }
        return new BookStats(preferHot,avgPrice, maxPrice, maxPriceBook, avgRanking, maxRanking, maxRankingBook, avgHot, maxHot, maxHotBook, minHot, minHotBook, bookList.size(), preAuthor);
    }


    private static void parseType(String type){
        //0-剧情，1-爱情，2-纪录片，3-惊悚，4-传记，5-战争，6-动画，7-奇幻，8-冒险
        String[] typeList = type.split("/");
        for (String ttype : typeList){
            ttype=ttype.trim();
            for(int i=0;i<15;++i){
                if(ttype.equals(refList[i])) typeRecord[i]++;
            }

        }
    }
    private static int stringCompare(String str1, String str2) {

        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);

        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int) str1.charAt(i);
            int str2_ch = (int) str2.charAt(i);

            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }

        if (l1 != l2) {
            return l1 - l2;
        } else {
            return 0;
        }
    }


}
