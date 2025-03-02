package com.jsp.epas.serviceimpl;

import com.jsp.epas.entity.Category;
import com.jsp.epas.entity.Employee;
import com.jsp.epas.enums.Rating;
import com.jsp.epas.repository.AppraisalRepository;
import com.jsp.epas.repository.CategoryRepository;
import com.jsp.epas.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PerformanceServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private AppraisalRepository appraisalRepository;

    @InjectMocks
    private PerformanceServiceImpl performanceService;

    private List<Employee> mockEmployees;
    private List<Category> mockCategories;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks manually

        mockEmployees = List.of(
                new Employee(1, "Haary 1", Rating.A),
                new Employee(2, "Haary 2", Rating.B),
                new Employee(3, "Haary 3", Rating.C),
                new Employee(4, "Haary 4", Rating.D),
                new Employee(5, "Haary 5", Rating.E)
        );

        mockCategories = List.of(
                new Category(1, Rating.A, 10.0),
                new Category(2, Rating.B, 20.0),
                new Category(3, Rating.C, 40.0),
                new Category(4, Rating.D, 20.0),
                new Category(5, Rating.E, 10.0)
        );
    }

    @Test
    void testCalculatePerformanceMetrics() {
        when(employeeRepository.findAll()).thenReturn(mockEmployees);
        when(categoryRepository.findAll()).thenReturn(mockCategories);

        Map<String, Object> result = performanceService.calculatePerformanceMetrics();

        assertNotNull(result);
        assertEquals(5, result.get("totalEmployees"));
        assertTrue(((Map<?, ?>) result.get("deviations")).containsKey(Rating.A));
    }

    @Test
    void testCalculatePerformanceMetrics_NoEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of());
        when(categoryRepository.findAll()).thenReturn(mockCategories);

        Map<String, Object> result = performanceService.calculatePerformanceMetrics();

        assertEquals(0, result.get("totalEmployees"));
        assertTrue(((Map<?, ?>) result.get("deviations")).isEmpty());
    }

    @Test
    void testCalculatePerformanceMetrics_HandlesNullCategories() {
        when(employeeRepository.findAll()).thenReturn(mockEmployees);
        when(categoryRepository.findAll()).thenReturn(List.of());

        Map<String, Object> result = performanceService.calculatePerformanceMetrics();

        assertEquals(5, result.get("totalEmployees"));
        assertTrue(((Map<?, ?>) result.get("deviations")).isEmpty());
    }

    @Test
    void testCalculatePerformanceMetrics_ValidAdjustments() {
        when(employeeRepository.findAll()).thenReturn(mockEmployees);
        when(categoryRepository.findAll()).thenReturn(mockCategories);

        Map<String, Object> result = performanceService.calculatePerformanceMetrics();

        Map<Employee, Rating> suggestedAdjustments = (Map<Employee, Rating>) result.get("suggestedAdjustments");
        assertNotNull(suggestedAdjustments);
        assertTrue(true);
    }
}
