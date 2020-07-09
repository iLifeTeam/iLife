### Spring JPA

---

遇到的问题：

- 通过级联Lazy读取的时候遇到error

  ```
  org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.ilife.zhihu.entity.Question.answerList, could not initialize proxy - no Session
  ```

  FetchType.lazy的关联默认不会被立刻取回，而等到访问到这个数据的时候，试图lazy取回，但和数据库的session已经关闭，无法lazy读取数据了。

  可以在需要访问数据的函数上使用@Transactional标签让session不关闭，或者使用FetchType.EAGER

