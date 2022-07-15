package com.example.demo.dao;


import com.example.demo.bo.AddMenuBO;
import com.example.demo.bo.UpdateMenuBO;
import com.example.demo.entity.MenuDO;
import com.example.demo.entity.RoleDO;
import com.example.demo.entity.RoleMenuDO;
import com.example.demo.entity.UserDO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao {

    @Select("select count(1) > 0 from hospital.user where user_id = #{userId} and password = #{password}")
    Boolean getUserByPassword(@Param("userId") Long userId, @Param("password") String password);

    @Select("<script>" +
            "select a.*, b.department_name,c.status_name,d.name role_name\n" +
            "from user a " + "left join department b on b.department_id = a.department_id\n" +
            "left join user_status c on c.status = a.status\n" +
            "left join role d on d.role_id = a.role_id\n" +
            "where !a.is_deleted " +
            "<if test='status != null'>" +
            "and a.`status` = #{status} " +
            "</if>" +
            "<if test='name != null '>" +
            "and (a.`name` like concat('%', #{name}, '%') or a.`telephone` like concat('%', #{name}, '%')) " +
            "</if>" +
            "<if test='departmentId != null '>" +
            "and a.department_id = #{departmentId} " +
            "</if>" +
            "</script>")
    List<UserDO> getAllUser(@Param("departmentId") Long departmentId, @Param("name") String name, @Param("status") Integer status);

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @Select("select count(1) > 0 from user_login where username= #{username} and password = #{password} and !is_deleted")
    boolean isLogin(@Param("username") String username, @Param("password") String password);

    @Select("select a.user_id , b.`name`, b.role_id\n" +
            "from user_login a\n" +
            "inner join user b on b.user_id = a.user_id \n" +
            "where !a.is_deleted and !b.is_deleted and a.username = #{username} and a.`password` = #{password} ")
    UserDO getUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Select("select a.*\n" +
            "from menu a \n" +
            "where exists(select b.menu_id from role_menu b where b.menu_id = a.menu_id and b.role_id = #{roleId})")
    List<MenuDO> getMenuList(Long roleId);

    @Select("select a.role_id, a.name, a.description, a.flag from role a")
    List<RoleDO> getRoleList();


    @Insert("insert into user(name, gender, department_id, telephone, status, role_id, created_id)\n" +
            "values \n" + "(#{name}, #{gender}, #{departmentId}, #{telephone}, #{status}, #{roleId}, #{createdId})")
    void saveUser(UserDO userDO);

    @Update("update user set name = #{name}, gender = #{gender}," +
            "department_id = #{departmentId}, telephone = #{telephone}, status = #{status}, role_id = #{roleId}, modifier_id = #{modifierId}, modified_date = #{modifiedDate}\n" +
            "where user_id = #{userId}")
    void updateUser(UserDO userDO);

    @Update("update user set is_deleted = 1 , deleter_id = #{userId}, deleted_date = NOW() \n" + "where user_id = #{deleteUserId}")
    void deleteUser(@Param("userId") Long userId, @Param("deleteUserId") Long deleteUserId);


    @Select("select *\n" + "from menu \n" + "where pid = #{menuId}")
    List<MenuDO> getSubmenuList(@Param("menuId") Long menuId);


    @Select("<script>" + "select a.*\n" + "from menu a\n" + "</script>")
    List<MenuDO> getAllMenuList();

    @Select("select count(1) > 0 from user_login where username =  #{username}")
    boolean getUserByUserName(String username);

    @Select("select a.*\n" +
            "from menu a \n" +
            "where exists(select b.menu_id from role_menu b where b.menu_id = a.menu_id and b.role_id = #{roleId})")
    List<MenuDO> getMenuListByRoleId(Long roleId);

    @Update("update role set name = #{name} , description = #{description} \n" +
            "where role_id = #{roleId}")
    void updateRole(UpdateMenuBO updateMenuBO);

    @Delete("delete from role_menu where role_id = #{roleId}")
    void deleteRoleMenu(Long roleId);

    @Insert("<script>\n" +
            "INSERT into role_menu(role_id, menu_id)\n" +
            "VALUES \n" +
            "<foreach collection='list' item='RoleMenuDO' separator=','>\n" +
            "   (#{RoleMenuDO.roleId}, #{RoleMenuDO.menuId})\n" +
            "</foreach>\n" +
            "</script>")
    void insertRoleMenu(@Param("list") List<RoleMenuDO> roleMenuList);

    @Insert("insert into role(name, description)\n" +
            "values \n" +
            "(#{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "roleId", keyColumn = "role_id")
    void addRole(UpdateMenuBO updateMenuBO);

    @Select("select count(*) > 0\n" +
            "from role a \n" +
            "where a.name = #{name}")
    boolean getRoleByName(String name);

    @Select("<script>" +
            "select a.*, b.name pidName\n" +
            "from menu a\n" +
            "LEFT JOIN (\n" +
            "SELECT menu_id, `name` from menu \n" +
            ") b on b.menu_id = a.pid \n" +
            "where" +
            "<if test='name != null '>" +
            " a.`name` like concat('%', #{name}, '%')  " +
            "</if>" +
            "</script>")
    List<MenuDO> getAllMenu(String name);

    @Select("<script>" +
            "select a.menu_id pid , a.name name\n" +
            "from menu a\n" +
            "where a.pid is null" +
            "</script>")
    List<MenuDO> getPidMenu();


    @Insert("insert into menu(name, path, icon, pid, pagePath, sortNum)\n" +
            "values \n" + "(#{name}, #{path}, #{icon}, #{pid}, #{pagePath}, null)")
    void addMenu(AddMenuBO addMenuBO);

    @Update("update menu set name = #{name} , path = #{path}, icon = #{icon}, pid = #{pid}, pagePath = #{pagePath} \n" +
            "where menu_id = #{menuId}")
    void updateMenu(AddMenuBO addMenuBO);
}
