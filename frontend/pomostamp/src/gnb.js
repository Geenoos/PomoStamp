import { Link } from "react-router-dom";

function Gnb() {
  return (
    <nav className="gnb">
      <ul>
        <li>
          <Link to="/">메인페이지</Link>
        </li>
        <li>
          <Link to="/createroom">방생성페이지</Link>
        </li>
        <li>
          <Link to="/studyroom/:2">스터디룸</Link>
        </li>
        <li>
          <Link to="/userpage">유저페이지</Link>
        </li>
        <li>
          <Link to="/todopage">ToDoList</Link>
        </li>
      </ul>
    </nav>
  );
}

export default Gnb;
