package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Student;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.StudentRepository;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://registerf-cst438.herokuapp.com/"})

public class StudentController {
	
		@Autowired
		StudentRepository studentRepository;
		
		@PostMapping("/student/new")
		@Transactional
		public StudentDTO addStudent( @RequestBody StudentDTO studentDTO  ) { 

			if(studentDTO.email == null)
				throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student email cannot be null");
			if(studentDTO.name == null)
				throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student name cannot be null");
			Student student = studentRepository.findByEmail(studentDTO.email);
			
			if(student == null) {
				student = new Student();
				student.setName(studentDTO.name);
				student.setEmail(studentDTO.email);
				student.setStatus(studentDTO.status);
				student.setStatusCode(studentDTO.statusCode);
				Student savedStudent = studentRepository.save(student);
				StudentDTO result = createStudentDTO(savedStudent);
				return result;
			}

			else{
				throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student already registered: "+studentDTO.email);
			}
			
		}
		
		@PostMapping("/student/addHold")
		@Transactional
		public StudentDTO addHold(  @RequestBody StudentDTO studentDTO  ) {
			Student student = studentRepository.findById(studentDTO.student_id).orElse(null);
			if(student!=null) {
			    student.setStatusCode(1);
			    student.setStatus("Administrative Hold");
			    Student savedStudent = studentRepository.save(student);
				StudentDTO result = createStudentDTO(savedStudent);
				return result;
			} else {
				throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student doesn't exist in the system: "+studentDTO.email);
			}
		}
		
		@PostMapping("/student/releaseHold")
		@Transactional
		public StudentDTO releaseHold(  @RequestBody StudentDTO studentDTO ) {
			Student student = studentRepository.findById(studentDTO.student_id).orElse(null);
			if(student!=null) {
			    student.setStatusCode(0);
			    student.setStatus(null);
			    Student savedStudent = studentRepository.save(student);
				StudentDTO result = createStudentDTO(savedStudent);
				return result;
			} else {
				throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student doesn't exist in the system: "+studentDTO.email);
			}
		}

		private StudentDTO createStudentDTO(Student s) {
			StudentDTO studentDTO = new StudentDTO();
			studentDTO.email = s.getEmail();
			studentDTO.name = s.getName();
			studentDTO.student_id = s.getStudent_id();
			studentDTO.statusCode = s.getStatusCode();
			return studentDTO;
	}
}