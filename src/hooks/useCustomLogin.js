import {Navigate, useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {login, loginPostAsync, logout} from "../slices/loginSlice";
import {useRecoilState, useResetRecoilState} from "recoil";
import {signinState} from "../atoms/signinState";
import {removeCookie, setCookie} from "../util/cookieUtil";
import {loginPost} from "../api/memberApi";
import {cartState} from "../atoms/cartState";


const useCustomLogin = () => {

	const [loginState , setLoginState] = useRecoilState(signinState)
	const resetState = useResetRecoilState(signinState)
	const resetCartState = useResetRecoilState(cartState)

	const isLogin = loginState.email ? true : false


	const navigate = useNavigate()

	// const dispatch = useDispatch()
	// const loginState = useSelector(state => state.loginSlice)



	const doLogin = async(loginParam) => {

		const result = await loginPost(loginParam)

		saveAsCookie(result)

		return result;

		// const action = await dispatch(loginPostAsync(loginParam))
		// return action.payload
	}

	// 리코용일
	const saveAsCookie = (data) => {
		setCookie("member",JSON.stringify(data),1)
		setLoginState(data)
	}

	const doLogout = () => {

		removeCookie('member');
		resetState();
		resetCartState();

		// dispatch(logout())
	}

	const moveToPath = (path) =>{
		navigate({pathname : path}, {replace:true})
	}

	const moveToLogin = () =>{
		navigate({pathname : '/member/login'}, {replace:true})
	}

	const moveToLoginReturn = () => {
		return <Navigate replace to={"/member/login"}/>
	}

	return  {loginState, isLogin, doLogin, doLogout, moveToPath, moveToLogin, moveToLoginReturn, saveAsCookie}



}

export default useCustomLogin