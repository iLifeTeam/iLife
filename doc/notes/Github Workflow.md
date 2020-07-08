### Github Workflow

---

```shell
开始编程前
$ git pull
$ git branch feature_branch_name
准备发布一个feature(暂时完善，可以merge到主干时)
$ git add .      (add准备提交的文件)
$ git commit -m "message"
$ git push -u feature_branch_name
在github中发起pull request，描述工作并指定reviewer.
```

[git常用指令参考](https://www.ruanyifeng.com/blog/2015/12/git-cheat-sheet.html)

---

### Git Commit message 规范

让使用你的代码的人更好的理解代码

**结构**

```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

- 可用的type: 

  fix, feat, build , chore, ci, docs, style, refactor, perf, test

- 可用的footer

  BREAKING CHANGE(或者简单的在type后面加一个！）： 表示重大的版本，API更新。

- 例子

  普通

  ```
  feat(parser): add ability to parse arrays
  ```

  使用Breaking change

  ```
  feat: allow provided config object to extend other configs
  
  BREAKING CHANGE: `extends` key in config file is now used for extending other config files
  ```

  使用！

  ```
  refactor!: drop support for Node 6
  ```

  文档

  ```
  docs: correct spelling of CHANGELOG
  ```