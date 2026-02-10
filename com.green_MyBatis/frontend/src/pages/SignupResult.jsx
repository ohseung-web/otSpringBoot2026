import { useLocation, Link } from 'react-router-dom';
import './Member.css';

export default function SignupResult() {
  const location = useLocation();
  // Signup.jsx에서 navigate 시 전달한 state 값을 받습니다.
  const result = location.state?.result;

  return (
    <section>
      <div id="section_wrap">
        <div className="word">회원 가입 결과</div>
        <div
          className="content"
          style={{ textAlign: 'center', padding: '50px 0' }}
        >
          {result > 0 ? (
            <div>
              <h2 style={{ color: '#2ecc71' }}>축하합니다! 회원가입 성공</h2>
              <p style={{ marginTop: '20px' }}>
                이제 로그인 후 서비스를 이용하실 수 있습니다.
              </p>
              <div className="btn_group" style={{ marginTop: '30px' }}>
                <Link to="/member/login">
                  <button>로그인하러 가기</button>
                </Link>
              </div>
            </div>
          ) : (
            <div>
              <h2 style={{ color: '#e74c3c' }}>회원가입 실패</h2>
              <p style={{ marginTop: '20px' }}>
                알 수 없는 오류가 발생했습니다. 다시 시도해 주세요.
              </p>
              <div className="btn_group" style={{ marginTop: '30px' }}>
                <Link to="/member/signup">
                  <button>다시 가입하기</button>
                </Link>
              </div>
            </div>
          )}
        </div>
      </div>
    </section>
  );
}
