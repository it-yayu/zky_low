以下是我们希望您遵循的准则
- [开发流程](#flow)
    - [代码风格与质量](#rules)
    - [版本管理](#commit)
- [开发工具](#)    
    
## 代码风格与质量

统一风格自动审查

### 编码规则{#rules}

为确保整个源代码的一致性，请务必遵守以下规则：

* 所有功能或错误修复必须通过一个或多个测试进行测试。
* 必须使用[Google Java编程风格指南](http://130.10.8.207/coding-specification/_book/code-specification/java-convention.html)来格式化Java文件。
* Web应用程序JavaScript文件必须遵循 [Google的JavaScript样式指南](http://130.10.8.207/coding-specification/_book/code-specification/javascript-convention.html)。

### 单元测试

在大项目中的单元测试尤为重要，避免代码裸奔，还可以结合Idea顺便做代码覆盖，目前Java的单元测试社区已经十分成熟活跃，可以参考Java单元测试与模拟测试，在提交Review之前，先本地强制跑过相关代码的单元测试
    生成的项目骨架中包含的有单元测试的例子，具体如何写单元测试请参照src/test目录下的测试用例

## 版本管理

代码审查(Review)

代码的Review是十分的必要的，通常我们需要在提交Review前，自己先Review修改，然后在提交给相关人员Review(通过gitlab的merge-request)，需要尽可能的保证以下规范:

* 尽量细的提交，保证代码尽可能的可追溯
* 如果多个Commit一起提交Review时，尽量保证相关性，以及可拆分性

各小组长做好代码审查工作

### Git提交指南

优秀统一的Commit Message Convention可以带来几大好处:

* 代码可追溯
* Commit类型与目的辨识度高
* 发布版本时，修改点一目了然，甚至可通过脚本自动生成

因此作为大团队，优秀的统一的Commit Message Convention是十分必要的，必须按照公司的[git提交信息规范](http://130.10.8.207/git-operation-guide/_book/fujian/section1.html) 详细信息参照

### Git 工作流(Git Flow)

Git Flow

清晰的Git Flow可以带来几大好处:

* 减少多团队协作带来错误的代码覆盖、冲突处理等问题
* 整个团队提交有规矩可寻，使得每个提交井然有序
* 清晰明辨统一的提交规范使得可追溯性明显的提高

Git 作为一个源码管理系统，不可避免涉及到多人协作。  
协作必须有一个规范的工作流程，让大家有效地合作，使得项目井井有条地发展下去。"工作流程"在英语里，叫做"workflow"或者"flow"，原意是水流，比喻项目像水流那样，顺畅、自然地向前流动，不会发生冲击、对撞、甚至漩涡
必须按照[git操作规范指南](http://130.10.8.207/git-operation-guide/_book/fujian/section1.html)

## 开发工具

根据实际情况选择：Eclipse或者Idea


## 安全相关

1. 在保证RBAC做功能级别的权限校验时，在设计系统时，同事应该考虑做数据级别的权限
数据权限控制的基本思路，一般是会执行的SQL语句中添加where条件，以便限定查出的数据， 
如 where 所属机构 in (用户可访问的机构） 
而不是在数据查询出来之后，再到代码中进行过滤——因为一般都会对数据进行高效分页，如果已经查询出来数据，再在代码中进行过滤的话，就可能出现一页数据不足一页的情况，数据总数也会与实际页面上查出的数据行不一致。

2.  极力避免明文密码。敏感数据最好要加密，建议使用加密