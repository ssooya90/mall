import {lazy, Suspense} from "react";
import {Navigate} from "react-router-dom";


const Loading = <div className={"bg-red-700"}>Loading...</div>
const ProductList = lazy(() => import("../pages/products/ListPage"))
const ProductAdd = lazy(() => import("../pages/products/AddPage"))
const ProductRead = lazy(() => import("../pages/products/ReadPage"))
const ProductModify = lazy(() => import("../pages/products/ModifyPage"))

const ProductsRouter = () => {
	return [
		{
			path: 'list',
			element: <Suspense fallback={Loading}><ProductList/></Suspense>
		},
		{
			path: 'add',
			element: <Suspense fallback={Loading}><ProductAdd/></Suspense>
		},
		{
			path : '',
			element: <Navigate replace to={'/products/list'}></Navigate>
		},
		{
			path : 'read/:pno',
			element: <Suspense fallback={Loading}><ProductRead/></Suspense>
		},
		{
			path : 'modify/:pno',
			element: <Suspense fallback={Loading}><ProductModify/></Suspense>
		}

	]
};

export default ProductsRouter;
