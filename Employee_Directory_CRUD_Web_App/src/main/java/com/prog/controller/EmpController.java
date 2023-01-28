package com.prog.controller;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prog.entity.Employee;
import com.prog.service.EmpService;

@Controller
public class EmpController {

	@Autowired
	private EmpService service;

	@GetMapping("/")
	public String home(Model m) {
		return findPaginated(0,"name", "asc", m);
	}

	@GetMapping("/addemp")
	public String addEmpForm() {
		return "add_emp";
	}

	@PostMapping("/register")
	public String empRegister(@ModelAttribute Employee e, HttpSession session) {
		service.addEmp(e);
		session.setAttribute("msg", "Emplyoee Added Sucessfully..");
		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model m) {
		Employee e = service.getEMpById(id);
		m.addAttribute("emp", e);
		return "edit";
	}

	@PostMapping("/update")
	public String updateEmp(@ModelAttribute Employee e, HttpSession session) {
		service.addEmp(e);
		session.setAttribute("msg", "Emp Data Update Sucessfully..");
		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String deleteEMp(@PathVariable int id, HttpSession session) {

		service.deleteEMp(id);
		session.setAttribute("msg", "Emp Data Delete Sucessfully..");
		return "redirect:/";
	}

	@GetMapping("/page/{pageno}")
	public String findPaginated(@PathVariable int pageno,@RequestParam("sortField") String sortField,
	        @RequestParam("sortDir") String sortDir, Model m) {
		
		int pageSize = 6;

		Page<Employee> page = service.getEMpByPaginate(pageno, pageSize, sortField, sortDir);
		m.addAttribute("emp", page);
		m.addAttribute("currentPage", pageno);
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("totalItem", page.getTotalElements());
		m.addAttribute("sortField", sortField);
        m.addAttribute("sortDir", sortDir);
        m.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		return "index";
	}

}