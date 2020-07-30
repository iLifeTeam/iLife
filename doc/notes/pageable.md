## 利用JPARepository实现数据分页返回
### 步骤
1. repository继承自JPARepository或者PageAndSortRepository（实际上JPARepository也是继承自PageAndSortRepository）
2. 在findbyid等方法上，新增一个类型为Pageable的参数，且此时返回类型要改为Page类型例如  
```java
public interface WyyhistoryRepository extends JpaRepository<wyyuser,Long> {
    List<wyyuser> findAllByWyyid(Long id);
    Page<wyyuser> findAllByWyyid(Long id, Pageable pageable);//第一个正常返回list，第二个函数则可以根据参数返回page<wyyuser>
};
```
3. 调用演示
```java
    @Test
    void findbyidsortedbypagetest(){
    Pageable pageable = PageRequest.of(5,10);
    System.out.println(wyyhistoryDao.findAllbyid((long)4，pageable));
    }//（5，10）分别代表页面的index和页面的size
```
4. Page的常用属性
   1. isEmpty()当前页面是否为空
   2. getContent()获取内容，返回List