package ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import admin.Admin;
import admin.AdminImpl;
import login.Login;
import login.LoginImpl;
import reserv.Reserv;
import reserv.ReservImpl;
import user.User;
import user.UserImpl;
import util.FileUtil;
import vo.AirInfoVO;
import vo.ReservVO;
import vo.UserVO;

public class MainUI {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	private int pageCnt = 0;
	private UserVO user = null;

	private List<UserVO> list_user = null;
	private List<AirInfoVO> list_info = null;
	private List<ReservVO> list_reserv = null;

//	생성자
	public MainUI() {

		list_user = fu.getUser();
		list_info = fu.getInfo();
		list_reserv = fu.getReserv();

		if (list_user.size() == 0) {
			UserVO admin = new UserVO();

			admin.setId("user");
			admin.setPw("1111");
			admin.setName("관리자");
			admin.setBirth("1900-01-01");

			list_user.add(admin);
		}
	}

//	Util 선언
	FileUtil fu = new FileUtil();

//	파일 불러오기

	public void menu() {
		aa: while (true) {
			if (user == null) {
//				로그인화면 UI
				loginPage();
				continue aa;
			} else if (user.getId().equals("user")) {
				pageCnt = 3;
			}

			switch (pageCnt) {
			case 1:
//				회원 페이지
				UserPage();
				break;
			case 2:
//				예약 페이지
				reservPage();
				break;
			case 3:
//				어드민 페이지
				adminPage();
				break;
			}
		}
	}

//	로그인 화면
	private void loginPage() {
		Login lg = new LoginImpl();
		try {
			char select;

			do {
				System.out.print("[1]로그인   [2]회원가입   [3]종료 => ");
				select = br.readLine().charAt(0);
			} while (select < '1' || select > '3');

			if (select == '3') {
				fu.save_UserF();
				fu.save_AirInfoF();
				fu.save_ReservF();

				System.exit(0);
			}

			switch (select) {
			case '1':
//				로그인 매서드 이동
				user = lg.UserLogin(list_user);

				if (user != null) {
					pageCnt = 1;
				}

				break;
			case '2':
//				회원가입 매서드 이동
				lg.addId(list_user);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	로그인 이후 화면
	private void UserPage() {
		User us = new UserImpl();
		try {
			char select;

			do {
				System.out.print("[1]예매   [2]조회   [3]회원정보수정  [4]회원탈퇴  [5]로그아웃 => ");
				select = br.readLine().charAt(0);
			} while (select < '1' || select > '5');

			if (select == '1') {
				pageCnt = 2;
				return;
			}

			if (select == '5') {
				user = null;
				return;
			}

			switch (select) {
			case '2':
//				조회 매서드
				us.reservSearch(user, list_user, list_reserv);
				break;
			case '3':
//				회원정보수정 매서드
				UserVO vo = us.updateUser(user, list_user);
				if (vo != null) {
					user = null;
					user = vo;
				}
				break;
			case '4':
//				회원탈퇴 매서드
				boolean check = us.deleteUser(user, list_user, list_reserv);

				if (check) {
					user = null;
					pageCnt = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	예약 화면
	private void reservPage() {
		Reserv re = new ReservImpl();
		try {
			char select;

			do {
				System.out.print("[1]예매   [2]취소   [3]뒤로가기 => ");
				select = br.readLine().charAt(0);
			} while (select < '1' || select > '3');

			if (select == '3') {
				pageCnt = 1;
				
				return;
			}

			switch (select) {
			case '1':
				re.reserv(list_reserv, list_info, user.getId());
				break;
//				예매 매서드
			case '2':
				re.cancleReserv(list_reserv, list_info, user.getId());
				break;
//				예매 취소 매서드
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	어드민 화면
	private void adminPage() {
		Admin ad = new AdminImpl();
		try {
			char select;

			do {
				System.out.println("[1]항공기목록  [2]항공기추가  [3]항공기삭제");
				System.out.print("[4]회원관리     [5]종료  => ");
				select = br.readLine().charAt(0);
			} while (select < '1' || select > '5');

			if (select == '5') {
				user = null;
				pageCnt = 0;
				fu.save_UserF();
				fu.save_AirInfoF();
				fu.save_ReservF();
				return;
			}

			switch (select) {
			case '1':
//				항공기 목록 조회 매서드
				ad.airList(list_info);
				break;
			case '2':
//				항공기 추가 매서드
				ad.addAirList(list_info);
				break;
			case '3':
//				항공기 삭제 매서드
				ad.deleteAirList(list_info, list_reserv);
				break;
			case '4':
//				회원 관리 매서드
				ad.userControl(list_user);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
