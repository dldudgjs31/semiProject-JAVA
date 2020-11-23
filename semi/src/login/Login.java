package login;

import java.util.List;

import vo.UserVO;

public interface Login {
//	로그인
	public UserVO UserLogin(List<UserVO> list_user);

//	회원가입
	public boolean addId(List<UserVO> list_user);

//	아이디대조
	public UserVO findById(String id, List<UserVO> list_user);
}
