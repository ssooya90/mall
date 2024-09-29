import {RouterProvider} from "react-router-dom";
import root from "./router/root";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {ReactQueryDevtools} from "@tanstack/react-query-devtools";

// 전체를 관리하기 때문에 app, index.js에 설치
const queryClient = new QueryClient()

function App() {
  return (
      <QueryClientProvider client={queryClient}>
        <RouterProvider router={root}/>
        <ReactQueryDevtools initalIsOpen={true}></ReactQueryDevtools>
      </QueryClientProvider>
  );
}

export default App;
