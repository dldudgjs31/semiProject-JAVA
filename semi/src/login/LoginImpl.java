package login;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import vo.UserVO;

public class LoginImpl implements Login {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

//	회원가입
	@Override
	public UserVO UserLogin(List<UserVO> list_user) {
		try {
			String id, pwd;
			
//			아이디를 입력받아 해당 아이디가 list_user(유저정보리스트)에 존재하는지 확인
			System.out.print("아이디 ? ");
			id = br.readLine();
		
			UserVO vo = findById(id, list_user);

			if (vo == null) {
				System.out.println("존재하지 않는 ID 입니다.");
				return null;
			}

//			입력한 비밀번호가 옳바른 비밀번호인지 확인
			System.out.print("비밀번호 ? ");
			pwd = br.readLine();

			if (!vo.getPw().equals(pwd)) {
				System.out.println("비밀번호가 옳바르지 않습니다.");
				return null;
			}

//			정상출력 메세지
			System.out.print(vo.getName() + "님 계정에 정상적으로 로그인 되었습니다.\n");
//			MainUI에서 UserVO user에 넣어주기 위해서 반환
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addId(List<UserVO> list_user) {
		try {
			String id;

//			아이디를 입력받아 해당 아이디가 이미 등록된 아이디가 맞는지 확인
			System.out.print("아이디 ?");
			id = br.readLine();
			
			UserVO vo = findById(id, list_user);

//			이미 존재한다면 메세지 출력
			if (vo != null) {
				System.out.println("이미 존재하는 아이디 입니다.\n");
				return false;
			}

//			존재하지않으면, 회원가입정보에 필요한 값을 받아 저장
			UserVO vo1 = new UserVO();
			vo1.setId(id);
			System.out.print("비밀번호 ? ");
			vo1.setPw(br.readLine());
			System.out.print("이름 ? ");
			vo1.setName(br.readLine());
			System.out.print("생년월일 ? ");
			vo1.setBirth(br.readLine());
			
			System.out.println("회원가입이 완료되었습니다.");
			
//			데이터가 널값이 아니라면 정상적으로 회원정보리스트에 추가
			if (vo1 != null) {
				list_user.add(vo1);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

//	아이디 중복 체크
//	true : 해당 아이디 존재
//	false : 해당 아이디 없음
	@Override
	public UserVO findById(String id, List<UserVO> list_user) {
		try {
			for (UserVO vo : list_user) {
				if (vo.getId().equals(id)) {
					return vo;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
