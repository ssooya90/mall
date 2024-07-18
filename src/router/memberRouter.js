import {lazy, Suspense} from "react";


const Loading = <div className={"bg-red-700"}>Loading...</div>
const Login = lazy(() => import("../pages/member/LoginPage"))

const memberRouter = () => {
	return [
		{
			path: 'login',
			element: <Suspense fallback={Loading}><Login/></Suspense>
		},

	]
};

export default memberRouter;
