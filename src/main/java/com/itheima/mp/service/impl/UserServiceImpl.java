package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.enums.UserStatus;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public void deductBalance(Long id, Integer money) {
        // 1.查询用户
        User user = getById(id);
        // 2.判断用户状态
        if (user == null || user.getStatus() == UserStatus.FREEZE) {
            throw new RuntimeException("用户状态异常");
        }
        // 3.判断用户余额
        if (user.getBalance() < money) {
            throw new RuntimeException("用户余额不足");
        }
        // 4.扣减余额
//        baseMapper.deductMoneyById(id, money);
        // 4.扣减余额 update tb_user set balance = balance - ?
        int remainBalance = user.getBalance() - money;
        lambdaUpdate()                                  	// ① 开启 lambda 更新链
                .set(User::getBalance, remainBalance)       // ② SET balance = 新余额
                .set(remainBalance == 0, User::getStatus, UserStatus.FREEZE)// ③ 条件 SET：余额为0才把状态改成冻结
                .eq(User::getId, id)                        // ④ WHERE id = ?
                .eq(User::getBalance, user.getBalance())    // ⑤ WHERE balance = 旧余额（乐观锁）
                .update();                                  // ⑥ 执行 UPDATE
    }
}