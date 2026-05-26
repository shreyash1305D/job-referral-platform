import { useState } from "react";
import API from "../api/axios";

export default function Login() {

  const [form, setForm] = useState({
    email: "",
    password: ""
  });

  const login = async () => {

    try {

      const res = await API.post(
        "/users/login",
        form
      );

      localStorage.setItem(
        "token",
        res.data.token
      );

      alert("Login Success");

    } catch (err) {

      alert("Invalid Login");
    }
  };

  return (
    <div>

      <h1>Login</h1>

      <input
        placeholder="Email"
        onChange={(e)=>
          setForm({...form,email:e.target.value})
        }
      />

      <input
        placeholder="Password"
        type="password"
        onChange={(e)=>
          setForm({...form,password:e.target.value})
        }
      />

      <button onClick={login}>
        Login
      </button>

    </div>
  );
}