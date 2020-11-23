package user;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import vo.ReservVO;
import vo.UserVO;

public class UserImpl implements User {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	@Override
	public boolean reservSearch(UserVO user, List<UserVO> list_user, List<ReservVO> list_reserv) {
		List<ReservVO> result = new LinkedList<ReservVO>();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String today = sdf.format(date);

		// 로그인된 사용자의 현재 시간 이후 예매내역 조회
		for (ReservVO vo : list_reserv) { 
			if (vo.getId().equals(user.getId()) && vo.getS_Day().compareTo(today) >= 0) {
				result.add(vo);
			}
		}

		// 출발날짜 오름차순으로 정렬
		Comparator<ReservVO> comp = new Comparator<ReservVO>() {

			@Override
			public int compare(ReservVO o1, ReservVO o2) {
				return o1.getS_Day().compareTo(o2.getS_Day());
			}
		};

		Collections.sort(result, comp);
		
		if (result.size() == 0) {
			System.out.print("예매된 내역이 없습니다.\n");
			return false;
		}
		
		System.out.println("\n[**예매내역**]\n");
		System.out.print("예약번호\t\t출발지\t도착지\t출발시간\n");
		for (ReservVO vo : result) {
			System.out.print(
					vo.getReservNo() + "\t" + vo.getDeparture() + "\t" + vo.getArrival() + "\t" + vo.getS_Day() + "\n");
		}

		return true;
	}

	@Override
	public UserVO updateUser(UserVO user, List<UserVO> list_user) {
		UserVO return_vo = null;
		char select;
		int cnt = 0;
		try {
			for (UserVO vo : list_user) {
				if (vo.getId().equals(user.getId())) {
					return_vo = vo;

					while (true) {
						System.out.print("[1]비밀번호변경  [2]이름변경  [3]생년월일변경 [4]종료  => ");
						select = br.readLine().charAt(0);

						if (select == '4') {
							if (cnt == 0) {
								System.out.println("변경된것이 없습니다.\n");
								return null;
							}
							System.out.println("변경이 완료되었습니다.\n");
							list_user.remove(vo);
							list_user.add(return_vo);
							return return_vo;
						}

						switch (select) {
						case '1':
							// 비밀번호변경
							System.out.print("변경할 비밀번호를 입력해주세요.");
							return_vo.setPw(br.readLine());
							break;
						case '2':
							// 이름변경
							System.out.print("변경할 이름을 입력해주세요.");
							return_vo.setName(br.readLine());
							break;
						case '3':
							// 생년월일변경
							System.out.print("변경할 생년월일을 입력해주세요");
							return_vo.setBirth(br.readLine());
							break;
						}
						cnt++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	//회원탈퇴
	@Override
	public boolean deleteUser(UserVO user, List<UserVO> list_user, List<ReservVO> list_reserv) {
		try {
			String pwd;

			System.out.print("본인확인을 위해서 비밀번호를 입력해주세요.");
			pwd = br.readLine();
			
			// 로그인된 사용자의 비밀번호 확인
			if (!checkPwd(pwd, user)) {
				System.out.println("비밀번호가 옳바르지 않습니다.");
				return false;
			}
			
			// 로그인된 사용자의 예매내역이 존재하는지 확인
			for (ReservVO vo : list_reserv) {
				if (vo.getId().equals(user.getId())) {
					System.out.println("예매된 표가 있습니다.");
					System.out.println("회원탈퇴를 원하시면 예매된 표를 취소해 주세요.");
					return false;
				}
			}
			
			for (UserVO vo : list_user) {
				if (vo.getId().equals(user.getId())) {
					list_user.remove(vo);
				}
			}

			System.out.print(user.getName() + "의 아이디가 정상적으로 삭제되었습니다.\n");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 비밀번호가 일치하는지 비교
	@Override
	public boolean checkPwd(String pwd, UserVO user) {
		if (pwd.equals(user.getPw())) {
			return true;
		}
		return false;
	}

}
