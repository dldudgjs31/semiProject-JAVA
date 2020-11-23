package user;

import java.util.List;

import vo.ReservVO;
import vo.UserVO;

public interface User {
//	비행기 예약 조회
	public boolean reservSearch(UserVO user, List<UserVO> list_user, List<ReservVO> list_reserv);

//	정보수정
	public UserVO updateUser(UserVO user, List<UserVO> list_user);

//	회원탈퇴
	public boolean deleteUser(UserVO user, List<UserVO> list_user, List<ReservVO> list_reserv);
	
//	비밀번호 확인
	public boolean checkPwd(String pwd, UserVO user);
}
